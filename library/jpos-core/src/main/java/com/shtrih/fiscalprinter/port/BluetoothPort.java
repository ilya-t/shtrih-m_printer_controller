/*
 * PrinterPort.java
 *
 * Created on August 30 2007, 12:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author V.Kravtsov
 */
package com.shtrih.fiscalprinter.port;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.Vector;

import org.apache.log4j.Logger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.shtrih.util.Localizer;

public class BluetoothPort implements PrinterPort {
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private int timeout = 10000;
	private int openTimeout = 10000;
	private String portName = "";
	private BluetoothSocket socket = null;
	static Logger logger = Logger.getLogger(BluetoothPort.class);

	/**
	 * Creates a new instance of BluetoothPort
	 */
	public BluetoothPort() {
	}

	public void checkOpened() throws Exception {
		if (socket == null) {
			throw new Exception("Port is not opened");
		}
	}

	public BluetoothSocket getPort() throws Exception {
		checkOpened();
		return socket;
	}

	@Override
	public void setPortName(String portName) throws Exception {
		logger.debug("setPortName('" + portName + "')");
		if (!this.portName.equalsIgnoreCase(portName)) {
			this.portName = portName;
		}
	}

	@Override
	public void setBaudRate(int baudRate) throws Exception {
	}

	public int getOpenTimeout() {
		return openTimeout;
	}

	public void setOpenTimeout(int openTimeout) {
		this.openTimeout = openTimeout;
	}

	@Override
	public void open(int timeout) throws Exception {
		if (isClosed()) {
			logger.debug("open");

			BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			if (adapter == null) {
				throw new Exception("Bluetooth not supported");
			}

			if (!adapter.isEnabled()) {
				throw new Exception("Bluetooth is not enabled");
			}

			if (adapter.isDiscovering()) {
				adapter.cancelDiscovery();
			}
			logger.debug("Adapter state: " + adapter.getState());
			switch (adapter.getState()) {
			case BluetoothAdapter.STATE_TURNING_ON: {
				waitBluetoothAdapterStateOn(adapter, openTimeout);
				break;

			}
			case BluetoothAdapter.STATE_TURNING_OFF: {
				throw new Exception("Bluetooth is turning off");
			}
			}
			logger.debug("adapter.getRemoteDevice");
			BluetoothDevice device = adapter.getRemoteDevice(portName);
			logger.debug("device.createRfcommSocketToServiceRecord");
			socket = device.createRfcommSocketToServiceRecord(MY_UUID);
			logger.debug("socket.connect()");
			socket.connect();
		}
	}

	public void waitBluetoothAdapterStateOn(BluetoothAdapter adapter,
			int timeout) throws Exception {
		logger.debug("waitBluetoothAdapterStateOn.0");

		long startTime = System.currentTimeMillis();
		for (;;) {
			long currentTime = System.currentTimeMillis();
			if (adapter.getState() == BluetoothAdapter.STATE_TURNING_ON) {
				Thread.sleep(100);
			} else {
				break;
			}
			if ((currentTime - startTime) > timeout) {
				throw new Exception("BluetoothAdapter turning on timeout");
			}
		}
		logger.debug("waitBluetoothAdapterStateOn.1");
	}

	@Override
	public synchronized void close() {
		if (isOpened()) {
			logger.debug("close");
			try {
				socket.close();
			} catch (Exception e) {
				logger.error(e);
			}
			socket = null;
		}
	}

	public synchronized boolean isOpened() {
		return socket != null;
	}

	public synchronized boolean isClosed() {
		return socket == null;
	}

	@Override
	public synchronized void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public synchronized void updateBaudRate() throws Exception {
	}

	public void readAll() throws Exception {
		InputStream stream = socket.getInputStream();
		stream.skip(stream.available());
	}

	private void noConnectionError() throws Exception {
		throw new IOException(Localizer.getString(Localizer.NoConnection));
	}

	public void connect() throws Exception {
		if (socket == null) {
			open(10000);
		}
	}

	@Override
	public void write(byte[] b) throws Exception {
		logger.debug("Write.0");
		connect();
		for (int i = 0; i < 2; i++) {
			try {
				if (i == 1)
					connect();
				OutputStream stream = getPort().getOutputStream();
				stream.write(b);
				stream.flush();
				return;
			} catch (IOException e) {
				socket.close();
				socket = null;
				if (i == 1)
					throw e;
				logger.error(e);
			}
		}
	}

	@Override
	public void write(int b) throws Exception {
		byte[] data = new byte[1];
		data[0] = (byte) b;
		write(data);
	}

	@Override
	public byte[] readBytes(int len) throws Exception {
		byte[] data = new byte[len];
		for (int i = 0; i < len; i++) {
			data[i] = (byte) doReadByte();
		}
		return data;
	}

	@Override
	public int readByte() throws Exception {
		int b = doReadByte();
		return b;
	}

	public int doReadByte() throws Exception {
		int result;
		InputStream is = getPort().getInputStream();
		long startTime = System.currentTimeMillis();
		for (;;) {
			long currentTime = System.currentTimeMillis();
			if (is.available() > 0) {
				result = is.read();
				if (result >= 0) {
					return result;
				}
			}
			if ((currentTime - startTime) > timeout) {
				noConnectionError();
			}
		}
	}

	public void flushOut() throws Exception {
		getPort().getOutputStream().flush();
	}

	public String[] getPortNames() throws Exception {
		logger.debug("getPortNames");
		Vector<String> result = new Vector<String>();
		result.add(BluetoothAdapter.getDefaultAdapter().getAddress());
		return result.toArray(new String[result.size()]);
	}

}