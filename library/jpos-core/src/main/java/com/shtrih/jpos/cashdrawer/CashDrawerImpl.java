/*
 * CashDrawerImpl.java
 *
 * Created on April 18 2008, 16:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.jpos.cashdrawer;

/**
 *
 * @author V.Kravtsov
 */
// java
import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import jpos.CashDrawerConst;
import jpos.JposConst;
import jpos.JposException;
import jpos.config.JposEntry;
import jpos.config.JposEntryConst;
import jpos.events.StatusUpdateEvent;
import jpos.services.CashDrawerService113;
import jpos.services.EventCallbacks;

import org.apache.log4j.Logger;

import com.shtrih.fiscalprinter.PrinterProtocol_1;
import com.shtrih.fiscalprinter.SMFiscalPrinterImpl;
import com.shtrih.fiscalprinter.command.LongPrinterStatus;
import com.shtrih.fiscalprinter.port.PrinterPort;
import com.shtrih.fiscalprinter.port.PrinterPortFactory;
import com.shtrih.jpos.DeviceService;
import com.shtrih.jpos.cashdrawer.directIO.CashDrawerDIOItem;
import com.shtrih.jpos.cashdrawer.directIO.DIOGetDriverParameter;
import com.shtrih.jpos.cashdrawer.directIO.DIOOpenDrawer;
import com.shtrih.jpos.cashdrawer.directIO.DIOReadDrawerState;
import com.shtrih.jpos.cashdrawer.directIO.DIOSetDriverParameter;
import com.shtrih.jpos.events.StatusUpdateEventRequest;
import com.shtrih.jpos.fiscalprinter.FptrParameters;
import com.shtrih.jpos.fiscalprinter.JposExceptionHandler;
import com.shtrih.util.Localizer;
import com.shtrih.util.Sound;

public class CashDrawerImpl extends DeviceService implements
		CashDrawerService113, CashDrawerConst, JposConst, JposEntryConst {
	// private data
	// ///////////////////////////////////////////////////////////////////////
	// internal state

	private boolean connected = false;
	private boolean claimed = false;
	private EventCallbacks cb = null;
	private int state = JPOS_S_CLOSED;
	private String checkHealthText = "";
	private boolean drawerOpened = false;
	private int powerState = JPOS_PS_UNKNOWN;
	private int powerNotify = JPOS_PN_DISABLED;
	private final String physicalDeviceName = "SHTRIM-M cash drawer";
	private final String physicalDeviceDescription = "SHTRIM-M fiscal printer cash drawer";
	// internal parameters
	private final int pollInterval = 500;
	private boolean deviceEnabled = false;
	// internal classes
	private Thread deviceThread = null; // device poll thread
	private Thread eventThread = null; // event delivery thread
	private final Vector events = new Vector();
	private PrinterPort port;
	private PrinterProtocol_1 device;
	private SMFiscalPrinterImpl printer;
	private static Logger logger = Logger.getLogger(CashDrawerImpl.class);
	private final CashDrawerParams params = new CashDrawerParams();
	private final FptrParameters fptrParams;
	private final CashDrawerStatistics statistics = new CashDrawerStatistics();
	private final HashMap dioItems = new HashMap(); // <Integer,
													// CashDrawerDIOItem>

	public CashDrawerImpl() throws Exception {
		fptrParams = new FptrParameters();
		statistics.unifiedPOSVersion = "1.13";
		statistics.deviceCategory = "CashDrawer";
		statistics.manufacturerName = "SHTRIH-M";
		statistics.modelName = "SHTRIH-M fiscal printer cash drawer";
		statistics.serialNumber = "";
		statistics.firmwareRevision = "";
		statistics.physicalInterface = RS232_DEVICE_BUS;
		statistics.installationDate = "";

		dioItems.put(new Integer(
				ShtrihCashDrawerConst.SMCASH_DIO_GET_DRIVER_PARAMETER),
				new DIOGetDriverParameter());

		dioItems.put(new Integer(
				ShtrihCashDrawerConst.SMCASH_DIO_SET_DRIVER_PARAMETER),
				new DIOSetDriverParameter());

		dioItems.put(new Integer(ShtrihCashDrawerConst.SMCASH_DIO_OPEN_DRAWER),
				new DIOOpenDrawer());

		dioItems.put(new Integer(
				ShtrihCashDrawerConst.SMCASH_DIO_READ_DRAWER_STATE),
				new DIOReadDrawerState());

		eventThread = new Thread(new EventTarget(this));
		eventThread.start();
	}

	public void handleException(Exception e) throws JposException {
		logger.error("handleException", e);
		JposExceptionHandler.handleException(e);
	}

	// private
	private void checkOpened() throws JposException {
		if (state == JPOS_S_CLOSED) {
			throw new JposException(JPOS_E_CLOSED);
		}
	}

	private void checkClaimed() throws JposException {
		if (!claimed) {
			throw new JposException(JPOS_E_NOTCLAIMED);
		}
	}

	private void checkEnabled() throws JposException {
		if (!getDeviceEnabled()) {
			throw new JposException(JPOS_E_DISABLED);
		}
	}

	private void checkOnLine() throws JposException {
		if (!connected) {
			throw new JposException(JPOS_E_OFFLINE);
		}
	}

	private void addEvent(Runnable event) {
		synchronized (events) {
			events.add(event);
			events.notifyAll();
		}
	}

	private void statusUpdateEvent(int value) {
		addEvent(new StatusUpdateEventRequest(cb, new StatusUpdateEvent(this,
				value)));
	}

	public void eventProc() {
		try {
			Thread thisThread = Thread.currentThread();
			while (eventThread == thisThread) {
				synchronized (events) {
					while (!events.isEmpty()) {
						((Runnable) events.remove(0)).run();
					}
					events.wait();
				}
			}
		} catch (InterruptedException e) {
			// Restore the interrupted status
			logger.error("InterruptedException", e);
			Thread.currentThread().interrupt();
		}
	}

	private void setPowerState(int powerState) {
		if (powerNotify == JPOS_PN_ENABLED) {
			if (powerState != this.powerState) {
				switch (powerState) {
				case JPOS_PS_ONLINE:
					statusUpdateEvent(JPOS_SUE_POWER_ONLINE);
					break;

				case JPOS_PS_OFF:
					statusUpdateEvent(JPOS_SUE_POWER_OFF);
					break;

				case JPOS_PS_OFFLINE:
					statusUpdateEvent(JPOS_SUE_POWER_OFFLINE);
					break;

				case JPOS_PS_OFF_OFFLINE:
					statusUpdateEvent(JPOS_SUE_POWER_OFF_OFFLINE);
					break;
				}
			}
		}
		this.powerState = powerState;
	}

	private void setDrawerOpened(boolean drawerOpened) {
		if (drawerOpened != this.drawerOpened) {
			if (drawerOpened) {
				statusUpdateEvent(CASH_SUE_DRAWEROPEN);
			} else {
				statusUpdateEvent(CASH_SUE_DRAWERCLOSED);
			}
			this.drawerOpened = drawerOpened;
		}
	}

	public void deviceProc() {
		try {
			Thread thisThread = Thread.currentThread();
			while (deviceThread == thisThread) {
				synchronized (printer) {
					try {
						connect();
						// update cash drawer state
						printer.readLongStatus().checkResultCode();
						LongPrinterStatus status = printer.getLongStatus();
						setDrawerOpened(status.getPrinterFlags()
								.isDrawerOpened());
						// update device parameters
						statistics.serialNumber = status.getSerial();
						statistics.firmwareRevision = status
								.getFirmwareRevision();
					} catch (Exception e) {
						logger.error(e);
						setPowerState(JPOS_PS_OFFLINE);
						connected = false;
					}
					printer.wait(pollInterval);
				}
			}
		} catch (InterruptedException e) {
			// Restore the interrupted status
			logger.error("InterruptedException", e);
			Thread.currentThread().interrupt();
		}
	}

	// Properties

	@Override
	public String getCheckHealthText() throws JposException {
		logger.debug("getCheckHealthText: " + checkHealthText);
		return checkHealthText;
	}

	@Override
	public boolean getClaimed() throws JposException {
		logger.debug("getClaimed: " + String.valueOf(claimed));

		checkOpened();
		return claimed;
	}

	@Override
	public boolean getDeviceEnabled() throws JposException {
		logger.debug("getDeviceEnabled: " + String.valueOf(deviceEnabled));
		return deviceEnabled;
	}

	@Override
	public void setDeviceEnabled(boolean deviceEnabled) throws JposException {
		logger.debug("setDeviceEnabled(" + String.valueOf(deviceEnabled) + ")");
		try {
			if (deviceEnabled != getDeviceEnabled()) {
				if (deviceEnabled) {
					claim(0);
					connect();

					deviceThread = new Thread(new DeviceTarget(this));
					deviceThread.start();
				} else {
					deviceThread = null;
					setPowerState(JPOS_PS_UNKNOWN);
				}
				this.deviceEnabled = deviceEnabled;
			}
		} catch (Exception e) {
			handleException(e);
		}
	}

	@Override
	public String getDeviceServiceDescription() throws JposException {
		String deviceServiceDescription = "Cash drawer service driver, 2008 SHTRIH-M";
		logger.debug("getDeviceServiceDescription: " + deviceServiceDescription);
		return deviceServiceDescription;
	}

	@Override
	public int getDeviceServiceVersion() throws JposException {
		int deviceServiceVersion = deviceVersion113 + 186;
		logger.debug("getDeviceServiceVersion: "
				+ String.valueOf(deviceServiceVersion));
		return deviceServiceVersion;
	}

	@Override
	public boolean getFreezeEvents() throws JposException {
		boolean freezeEvents = eventThread == null;
		logger.debug("getFreezeEvents: " + String.valueOf(freezeEvents));

		checkOpened();
		return freezeEvents;
	}

	@Override
	public void setFreezeEvents(boolean freezeEvents) throws JposException {
		logger.debug("setFreezeEvents(" + String.valueOf(freezeEvents) + ")");

		checkOpened();
		if (freezeEvents != getFreezeEvents()) {
			if (freezeEvents) {
				eventThread = null;
			} else {
				eventThread = new Thread(new EventTarget(this));
				eventThread.start();
			}
		}
	}

	@Override
	public String getPhysicalDeviceDescription() throws JposException {
		logger.debug("getPhysicalDeviceDescription: "
				+ physicalDeviceDescription);

		checkOpened();
		return physicalDeviceDescription;
	}

	@Override
	public String getPhysicalDeviceName() throws JposException {
		logger.debug("getPhysicalDeviceName: " + physicalDeviceName);

		checkOpened();
		return physicalDeviceName;
	}

	@Override
	public int getState() throws JposException {
		logger.debug("getState: " + String.valueOf(state));
		return state;
	}

	// Methods supported by all device services.

	@Override
	public void claim(int timeout) throws JposException {
		logger.debug("claim(" + String.valueOf(timeout) + ")");
		try {
			checkOpened();
			if (!claimed) {
				port.setPortName(fptrParams.portName);
				port.setBaudRate(fptrParams.baudRate);
				port.open(timeout);
				claimed = true;
			}
		} catch (Exception e) {
			handleException(e);
		}
		logger.debug("claim: OK");
	}

	@Override
	public void close() throws JposException {
		logger.debug("close");
		checkOpened();
		if (claimed) {
			release();
		}
		state = JPOS_S_CLOSED;
		statistics.save(fptrParams.statisticFileName);
		logger.debug("close: OK");
	}

	@Override
	public void checkHealth(int level) throws JposException {
		logger.debug("checkHealth(" + String.valueOf(level) + ")");

		try {
			checkEnabled();
			if (level == JPOS_CH_EXTERNAL) {
				checkHealthText = "";
				openDrawer();
				return;
			}
			if (level == JPOS_CH_INTERACTIVE) {
				checkHealthText = "";
				// new CheckHealthDialog(this)
				return;
			}
			throw new JposException(JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.invalidParameterValue
							+ "level"));

		} catch (Exception e) {
			handleException(e);
		}
		logger.debug("checkHealth: OK");
	}

	@Override
	public void directIO(int command, int[] data, Object object)
			throws JposException {
		logger.debug("directIO(" + String.valueOf(command) + ", "
				+ String.valueOf(data) + ", " + String.valueOf(object) + ")");

		try {
			CashDrawerDIOItem dioItem = (CashDrawerDIOItem) dioItems
					.get(new Integer(command));
			if (dioItem == null) {
				throw new JposException(JPOS_E_ILLEGAL,
						Localizer.getString(Localizer.NotImplemented));
			}
			dioItem.execute(this, data, object);
		} catch (Exception e) {
			handleException(e);
		}
		logger.debug("directIO: OK");
	}

	@Override
	public void open(String logicalName, EventCallbacks cb)
			throws JposException {
		logger.debug("open");
		logger.debug("SHTRIH-M JavaPos CashDrawer service");
		logger.debug("DeviceServiceVersion: "
				+ String.valueOf(getDeviceServiceVersion()));
		try {
			this.cb = cb;
			params.load(jposEntry, fptrParams);

			Enumeration entries = jposEntry.getRegPopulator().getEntries();
			while (entries.hasMoreElements()) {
				JposEntry entry = (JposEntry) entries.nextElement();
				String deviceName = (String) entry
						.getPropertyValue(JposEntry.LOGICAL_NAME_PROP_NAME);
				if (deviceName.equalsIgnoreCase(params.fiscalPrinterDevice)) {
					fptrParams.load(entry);
				}
			}
			port = PrinterPortFactory.createInstance(fptrParams);
			device = new PrinterProtocol_1(port);
			printer = new SMFiscalPrinterImpl(port, device, fptrParams);
			statistics.load(fptrParams.statisticFileName);
			device.setByteTimeout(fptrParams.byteTimeout);
			state = JPOS_S_IDLE;
		} catch (Exception e) {
			handleException(e);
		}
		logger.debug("open: OK");
	}

	@Override
	public void release() throws JposException {
		logger.debug("release");
		try {
			checkClaimed();
			setDeviceEnabled(false);
			claimed = false;
			synchronized (printer) {
				printer.closePort();
			}
		} catch (Exception e) {
			JposExceptionHandler.handleException(e);
		}
		logger.debug("release: OK");
	}

	// Capabilities

	@Override
	public boolean getCapStatus() throws JposException {
		logger.debug("getCapStatus: " + String.valueOf(params.capStatus));
		return params.capStatus;
	}

	@Override
	public boolean getCapStatusMultiDrawerDetect() throws JposException {
		boolean value = false;
		logger.debug("getCapStatusMultiDrawerDetect: " + String.valueOf(value));
		return value;
	}

	@Override
	public boolean getCapStatisticsReporting() throws JposException {
		boolean value = true;
		logger.debug("getCapStatisticsReporting: " + String.valueOf(value));
		return value;
	}

	@Override
	public boolean getCapUpdateStatistics() throws JposException {
		boolean value = true;
		logger.debug("getCapUpdateStatistics: " + String.valueOf(value));
		return value;
	}

	@Override
	public int getCapPowerReporting() throws JposException {
		logger.debug("getCapPowerReporting: JPOS_PR_STANDARD");
		return JPOS_PR_STANDARD;
	}

	@Override
	public boolean getCapCompareFirmwareVersion() throws JposException {
		logger.debug("getCapCompareFirmwareVersion: false");
		return false;
	}

	@Override
	public boolean getCapUpdateFirmware() throws JposException {
		logger.debug("getCapUpdateFirmware: false");
		return false;
	}

	// Properties

	@Override
	public boolean getDrawerOpened() throws JposException {
		logger.debug("getDrawerOpened: " + String.valueOf(drawerOpened));
		checkEnabled();
		return drawerOpened;
	}

	@Override
	public int getPowerNotify() throws JposException {
		logger.debug("getPowerNotify: " + String.valueOf(powerNotify));
		return powerNotify;
	}

	@Override
	public void setPowerNotify(int powerNotify) throws JposException {
		logger.debug("setPowerNotify(" + String.valueOf(powerNotify) + ")");

		if (deviceEnabled) {
			throw new JposException(JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.deviceIsEnabled));
		}
		this.powerNotify = powerNotify;
	}

	@Override
	public int getPowerState() throws JposException {
		logger.debug("getPowerState: " + String.valueOf(powerState));
		return powerState;
	}

	// Methods

	@Override
	public void openDrawer() throws JposException {
		logger.debug("openDrawer()");
		try {
			checkEnabled();
			checkOnLine();
			synchronized (printer) {
				printer.openCashDrawer(params.drawerNumber);
			}
		} catch (Exception e) {
			statistics.drawerOpenFailed();
			JposExceptionHandler.handleException(e);
		}
	}

	@Override
	public void waitForDrawerClose(int beepTimeout, int beepFrequency,
			int beepDuration, int beepDelay) throws JposException {
		logger.debug("waitForDrawerClose(" + String.valueOf(beepTimeout) + ", "
				+ String.valueOf(beepFrequency) + ", "
				+ String.valueOf(beepDuration) + ", "
				+ String.valueOf(beepDelay) + ")");

		checkEnabled();
		checkOnLine();
		try {
			long timeLeft = 0;
			long currentTime = 0;
			long startTime = System.currentTimeMillis();
			while (drawerOpened) {
				currentTime = System.currentTimeMillis();
				timeLeft = startTime - currentTime;
				if (timeLeft > beepTimeout) {
					Sound.beep(beepFrequency, beepDuration);
					Thread.sleep(beepDelay);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new JposException(JPOS_E_FAILURE, e.getMessage(), e);
		}
		logger.debug("waitForDrawerClose: OK");
	}

	@Override
	public void resetStatistics(String statisticsBuffer) throws JposException {
		logger.debug("resetStatistics(" + statisticsBuffer + ")");

		checkEnabled();
		statistics.reset(statisticsBuffer);
	}

	@Override
	public void retrieveStatistics(String[] statisticsBuffer)
			throws JposException {
		logger.debug("retrieveStatistics(" + statisticsBuffer[0] + ")");

		checkEnabled();
		statistics.retrieve(statisticsBuffer);
	}

	@Override
	public void updateStatistics(String statisticsBuffer) throws JposException {
		logger.debug("updateStatistics(" + statisticsBuffer + ")");

		checkEnabled();
		statistics.update(statisticsBuffer);
	}

	@Override
	public void compareFirmwareVersion(String firmwareFileName, int[] result)
			throws JposException {
		logger.debug("compareFirmwareVersion(" + firmwareFileName + ")");
		checkEnabled();
		throw new JposException(JPOS_E_ILLEGAL);
	}

	@Override
	public void updateFirmware(String firmwareFileName) throws JposException {
		logger.debug("updateFirmware(" + firmwareFileName + ")");

		checkEnabled();
		throw new JposException(JPOS_E_ILLEGAL);
	}

	public boolean searchByBaudRates(int deviceBaudRate, int deviceByteTimeout,
			boolean searchByBaudRateEnabled) throws Exception {
		if (searchByBaudRateEnabled) {
			int[] baudRates = printer.getSupportedBaudRates();
			for (int j = 0; j < baudRates.length; j++) {
				if (printer.connectDevice(baudRates[j], deviceBaudRate,
						deviceByteTimeout)) {
					return true;
				}
			}
		}
		return false;
	}

	public void connect() throws Exception {
		// connect, if not yet connected
		if (!connected) {
			searchDevice();
			setPowerState(JPOS_PS_ONLINE);
			connected = true;
		}
	}

	public void searchDevice() throws Exception {
		logger.debug("connect");
		FptrParameters fptrParams = printer.getParams();
		if (printer.connectDevice(fptrParams.baudRate, fptrParams.baudRate,
				fptrParams.deviceByteTimeout)) {
			return;
		}

		if (fptrParams.searchByPortEnabled) {
			Enumeration e = CommPortIdentifier.getPortIdentifiers();
			while (e.hasMoreElements()) {
				CommPortIdentifier portIdentifier = (CommPortIdentifier) e
						.nextElement();
				if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (!fptrParams.portName.equalsIgnoreCase(portIdentifier
							.getName())) {
						port.setPortName(portIdentifier.getName());
						port.setBaudRate(fptrParams.baudRate);
						port.open(0);
						if (searchByBaudRates(fptrParams.baudRate,
								fptrParams.deviceByteTimeout,
								fptrParams.searchByBaudRateEnabled)) {
							return;
						}
					}
				}
			}
		} else {
			if (searchByBaudRates(fptrParams.baudRate,
					fptrParams.deviceByteTimeout,
					fptrParams.searchByBaudRateEnabled)) {
				return;
			}
		}
		throw new JposException(JPOS_E_NOHARDWARE);
	}

	class DeviceTarget implements Runnable {

		private final CashDrawerImpl cashDrawer;

		public DeviceTarget(CashDrawerImpl cashDrawer) {
			this.cashDrawer = cashDrawer;
		}

		@Override
		public void run() {
			cashDrawer.deviceProc();
		}
	}

	class EventTarget implements Runnable {

		private final CashDrawerImpl cashDrawer;

		public EventTarget(CashDrawerImpl cashDrawer) {
			this.cashDrawer = cashDrawer;
		}

		@Override
		public void run() {
			cashDrawer.eventProc();
		}
	}

	public SMFiscalPrinterImpl getPrinter() {
		return printer;
	}

	public CashDrawerParams getParams() {
		return params;
	}
}
