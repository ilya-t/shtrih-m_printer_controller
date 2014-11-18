/*
 * SmPrinterDevice.java
 *
 * Created on July 31 2007, 16:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author V.Kravtsov
 */
package com.shtrih.fiscalprinter;

import java.io.ByteArrayOutputStream;
import java.security.InvalidParameterException;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.shtrih.barcode.BarcodeEncoderFactory;
import com.shtrih.barcode.PrinterBarcode;
import com.shtrih.barcode.SmBarcodeEncoder;
import com.shtrih.ej.EJDate;
import com.shtrih.fiscalprinter.command.*;
import com.shtrih.fiscalprinter.model.PrinterModel;
import com.shtrih.fiscalprinter.model.PrinterModels;
import com.shtrih.fiscalprinter.port.PrinterPort;
import com.shtrih.fiscalprinter.table.PrinterField;
import com.shtrih.fiscalprinter.table.PrinterFields;
import com.shtrih.fiscalprinter.table.PrinterTable;
import com.shtrih.fiscalprinter.table.PrinterTables;
import com.shtrih.jpos.fiscalprinter.FptrParameters;
import com.shtrih.jpos.fiscalprinter.PrinterImages;
import com.shtrih.jpos.fiscalprinter.SmFptrConst;
import com.shtrih.printer.ncr7167.CommandContext;
import com.shtrih.printer.ncr7167.NCR7167Printer;
import com.shtrih.util.Hex;
import com.shtrih.util.Localizer;
import com.shtrih.util.MethodParameter;
import com.shtrih.util.StringUtils;

public class SMFiscalPrinterImpl implements SMFiscalPrinter, PrinterConst {

	public PrinterProtocol device;
	// delay on wait
	public static final int TimeToSleep = 100;
	public static String charsetName = "Cp1251"; // device charset name
	// tax officer password
	public int taxPassword = 0;
	// operator password
	public int usrPassword = 1;
	// system administrator password
	public int sysPassword = 30;
	public boolean wrapText = true;
	private PrinterModel model = null;
	private final PrinterModels models = new PrinterModels();
	private FlexCommands commands = null;
	private DeviceMetrics deviceMetrics = new DeviceMetrics();
	private LongPrinterStatus longStatus = new LongPrinterStatus();
	private ShortPrinterStatus shortStatus = new ShortPrinterStatus();
	/**
	 * Cashed field info *
	 */
	public ReadTableInfo tableInfo = new ReadTableInfo();
	public ReadFieldInfo fieldInfo = new ReadFieldInfo();
	public static Logger logger = Logger.getLogger(SMFiscalPrinterImpl.class);
	private final Vector events = new Vector();
	private final PrinterPort port;
	private final FptrParameters params;
	private final PrinterImages printerImages = new PrinterImages();
	private int resultCode = 0;
	private NCR7167Printer escPrinter = new NCR7167Printer(null);

	public SMFiscalPrinterImpl(PrinterPort port, PrinterProtocol device,
			FptrParameters params) {
		this.port = port;
		this.device = device;
		this.params = params;
		fieldInfo.setPassword(sysPassword);
		models.load();
		try {
			model = models.itemByID(SMFP_MODELID_DEFAULT);
		} catch (Exception e) {
		}
	}

	@Override
	public int getResultCode() {
		return resultCode;
	}

	@Override
	public String getResultText() {
		try {
			return PrinterError.getFullText(resultCode);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void setEscPrinter(NCR7167Printer escPrinter) {
		this.escPrinter = escPrinter;
	}

	@Override
	public FptrParameters getParams() {
		return params;
	}

	@Override
	public PrinterImages getPrinterImages() {
		return printerImages;
	}

	@Override
	public void setDevice(PrinterProtocol device) {
		this.device = device;
	}

	@Override
	public PrinterProtocol getDevice() {
		return device;
	}

	public static String getCharsetName() {
		return charsetName;
	}

	@Override
	public void addEvents(IPrinterEvents item) {
		events.add(item);
	}

	@Override
	public void deviceExecute(PrinterCommand command) throws Exception {
		beforeCommand(command);
		device.send(command);
		afterCommand(command);
	}

	public void beforeCommand(PrinterCommand command) {
		for (int i = 0; i < events.size(); i++) {
			IPrinterEvents printerEvents = (IPrinterEvents) events.get(i);
			try {
				printerEvents.beforeCommand(command);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	public void afterCommand(PrinterCommand command) {
		for (int i = 0; i < events.size(); i++) {
			IPrinterEvents printerEvents = (IPrinterEvents) events.get(i);
			try {
				printerEvents.afterCommand(command);
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void connect() throws Exception {
		device.connect();
	}

	@Override
	public void check(int errorCode) throws Exception {
		if (errorCode != 0) {
			String text = PrinterError.getFullText(errorCode);
			throw new SmFiscalPrinterException(errorCode, text);
		}
	}

	@Override
	public void execute(PrinterCommand command) throws Exception {
		check(executeCommand(command));
	}

	@Override
	public int getSysPassword() {
		return sysPassword;
	}

	@Override
	public int getUsrPassword() {
		return usrPassword;
	}

	@Override
	public int getTaxPassword() {
		return taxPassword;
	}

	@Override
	public boolean failed(int errorCode) {
		return errorCode != 0;
	}

	@Override
	public boolean succeeded(int errorCode) {
		return errorCode == 0;
	}

	@Override
	public int executeCommand(PrinterCommand command) throws Exception {
		String text = Hex.toHex((byte) command.getCode()) + "h, "
				+ command.getText();
		logger.debug(text);

		command.setTimeout(getCommandTimeout(command.getCode()));
		while (true) {
			deviceExecute(command);
			resultCode = command.getResultCode();
			switch (resultCode) {
			case PrinterError.E_PRINTER_PREVCOMMAND:
				waitForPrinting();
				break;

			case PrinterError.E_PRINTER_WAITPRINT:
				continuePrint();
				waitForPrinting();
				break;

			default:
				return resultCode;
			}
		}
	}

	@Override
	public void setTaxPassword(int taxPassword) {
		this.taxPassword = taxPassword;
	}

	@Override
	public void setUsrPassword(int usrPassword) {
		this.usrPassword = usrPassword;
	}

	@Override
	public void setSysPassword(int sysPassword) {
		this.sysPassword = sysPassword;
	}

	@Override
	public Beep beep() throws Exception {
		logger.debug("beep");
		Beep command = new Beep();
		command.setPassword(usrPassword);
		execute(command);
		return command;
	}

	@Override
	public ReadEJStatus readEJStatus() throws Exception {
		logger.debug("readEJStatus");
		ReadEJStatus command = new ReadEJStatus();
		command.setPassword(sysPassword);
		executeCommand(command);
		return command;
	}

	@Override
	public int activateEJ() throws Exception {
		logger.debug("activateEJ");
		ActivateEJCommand command = new ActivateEJCommand();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int closeEJArchive() throws Exception {
		logger.debug("closeEJArchive");
		CloseEJArhive command = new CloseEJArhive();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int initEJArchive() throws Exception {
		logger.debug("initEJArchive");
		InitEJArchive command = new InitEJArchive();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int testEJArchive() throws Exception {
		logger.debug("testEJArchive");
		TestEJArchive command = new TestEJArchive();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int stopEJPrint() throws Exception {
		logger.debug("stopEJPrint");
		StopEJPrint command = new StopEJPrint(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int readEJDayReport(int dayNumber) throws Exception {
		logger.debug("readEJDayReport");
		ReadEJDayReport command = new ReadEJDayReport(sysPassword, dayNumber);
		return executeCommand(command);
	}

	@Override
	public int readEJDayTotals(int dayNumber) throws Exception {
		logger.debug("readEJDayTotals");
		ReadEJDayTotals command = new ReadEJDayTotals(sysPassword, dayNumber);
		return executeCommand(command);
	}

	@Override
	public int printEJDayReport(int dayNumber) throws Exception {
		logger.debug("printEJDayReport");
		PrintEJDayReport command = new PrintEJDayReport(sysPassword, dayNumber);
		return executeCommand(command);
	}

	@Override
	public int printEJDayTotal(int dayNumber) throws Exception {
		logger.debug("printEJDayTotal");
		PrintEJDayTotal command = new PrintEJDayTotal(sysPassword, dayNumber);
		return executeCommand(command);
	}

	@Override
	public int printEJDocument(int macNumber) throws Exception {
		logger.debug("PrintEJDocument");
		PrintEJDocument command = new PrintEJDocument(sysPassword, macNumber);
		return executeCommand(command);
	}

	@Override
	public int printEJActivationReport() throws Exception {
		logger.debug("printEJActivationReport");
		PrintEJActivationReport command = new PrintEJActivationReport(
				sysPassword);
		return executeCommand(command);
	}

	@Override
	public int cancelEJDocument() throws Exception {
		logger.debug("cancelEJDocument");
		CancelEJDocument command = new CancelEJDocument();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int writeEJErrorCode(int errorCode) throws Exception {
		logger.debug("writeEJErrorCode");
		WriteEJErrorCode command = new WriteEJErrorCode(sysPassword, errorCode);
		return executeCommand(command);
	}

	@Override
	public ReadLongStatus readLongStatus() throws Exception {
		logger.debug("readLongStatus");
		ReadLongStatus command = new ReadLongStatus();
                command.setPassword(usrPassword);
		executeCommand(command);
		if (command.isSucceeded()) {
			longStatus = command.getStatus();
		}
		return command;
	}

	@Override
	public LongPrinterStatus getLongStatus() {
		return longStatus;
	}

	@Override
	public ShortPrinterStatus getShortStatus() {
		return shortStatus;
	}

	@Override
	public ShortPrinterStatus readShortStatus() throws Exception {
		logger.debug("readShortStatus");
		ReadShortStatus command = new ReadShortStatus(usrPassword);
		execute(command);
		shortStatus = command.getStatus();
		return shortStatus;
	}

	@Override
	public int printString(int station, String line) throws Exception {
		logger.debug("printString(" + String.valueOf(station) + ", '" + line
				+ "')");

		if (line == null) {
			throw new InvalidParameterException("printString, line = null");
		}

		PrintString command = new PrintString(usrPassword, station, line);
		execute(command);
		return command.getOperator();
	}

	@Override
	public int printBoldString(int station, String line) throws Exception {
		logger.debug("printBoldString(" + String.valueOf(station) + ", '"
				+ line + "')");

		PrintBoldString command = new PrintBoldString();
		command.setPassword(usrPassword);
		command.setStation(station);
		command.setText(line);
		execute(command);
		return command.getOperator();
	}

	@Override
	public void feedPaper(int station, int lineNumber) throws Exception {
		logger.debug("feedPaper");
		FeedPaper command = new FeedPaper();
		command.setPassword(usrPassword);
		command.setStations(station);
		command.setLineNumber(lineNumber);
		execute(command);
	}

	@Override
	public int printStringFont(int station, FontNumber font, String line)
			throws Exception {
		logger.debug("printStringFont(" + String.valueOf(station) + ", '"
				+ String.valueOf(font.getValue()) + ", '" + line + "')");

		PrintStringFont command = new PrintStringFont(usrPassword, station,
				font, line);

		execute(command);
		return command.getOperator();
	}

	// line is truncated to maximum print width

	@Override
	public int printLine(int station, String line, FontNumber font)
			throws Exception {
		logger.debug("printLine(" + String.valueOf(station) + ", " + "'" + line
				+ "', " + String.valueOf(font.getValue()) + ")");

		if (line.length() == 0) {
			line = " ";
		}
		int len = Math.min(line.length(), getModel().getTextLength(font));
		line = line.substring(0, len);
		if (getModel().getCapPrintStringFont()) {
			return printStringFont(station, font, line);
		} else {
			if (font.getValue() == PrinterConst.FONT_NUMBER_DOUBLE) {
				return printBoldString(station, line);
			} else {
				return printString(station, line);
			}
		}
	}

	@Override
	public String[] splitText(String text, int n, boolean wrap) {
		boolean isLineEnd = false;
		String line = "";
		Vector lines = new Vector();
		if (text.length() == 0) {
			lines.add("");
		} else {
			for (int i = 0; i < text.length(); i++) {
				switch (text.charAt(i)) {
				case '\r':
					break;

				case '\n':
					if (!isLineEnd) {
						lines.add(line);
						line = "";
						isLineEnd = true;
					}
					break;

				default:
					isLineEnd = false;
					line = line + text.charAt(i);
					if (wrap) {
						if (line.length() == n) {
							lines.add(line);
							line = "";
							isLineEnd = true;
						}
					}
				}
			}
			if (line.length() != 0) {
				lines.add(line);
			}
		}
		return (String[]) lines.toArray(new String[0]);
	}

	@Override
	public String[] splitText(String text, FontNumber font) throws Exception {
		int len = getModel().getTextLength(font);
		return splitText(text, len, wrapText);
	}

	@Override
	public int updateFieldInfo(int tableNumber, int fieldNumber)
			throws Exception {
		int result = 0;
		if (!fieldInfo.isEqual(tableNumber, fieldNumber)) {
			ReadFieldInfo readFieldInfo = new ReadFieldInfo();
			readFieldInfo.setPassword(sysPassword);
			readFieldInfo.setTableNumber(tableNumber);
			readFieldInfo.setFieldNumber(fieldNumber);
			result = executeCommand(readFieldInfo);
			if (succeeded(result)) {
				fieldInfo = readFieldInfo;
			}
		}
		return result;
	}

	@Override
	public int writeTable(int tableNumber, int rowNumber, int fieldNumber,
			String fieldValue) throws Exception {
		int result = 0;
		logger.debug("writeTable(" + String.valueOf(tableNumber) + ", "
				+ String.valueOf(rowNumber) + ", "
				+ String.valueOf(fieldNumber) + ", " + "'" + fieldValue + "')");

		// update field info
		result = updateFieldInfo(tableNumber, fieldNumber);
		if (failed(result)) {
			return result;
		}

		byte[] fieldData = fieldInfo.fieldToBytes(fieldValue, charsetName);
		PrinterCommand command2 = new WriteTable(sysPassword, tableNumber,
				rowNumber, fieldNumber, fieldData);
		return executeCommand(command2);
	}

	@Override
	public int readTable(int tableNumber, int rowNumber, int fieldNumber,
			String[] fieldValue) throws Exception {
		int result = 0;
		logger.debug("readTable(" + String.valueOf(tableNumber) + ", "
				+ String.valueOf(rowNumber) + ", "
				+ String.valueOf(fieldNumber) + ")");

		// update field info
		result = updateFieldInfo(tableNumber, fieldNumber);
		if (failed(result)) {
			return result;
		}

		ReadTable commandReadTable = new ReadTable(sysPassword, tableNumber,
				rowNumber, fieldNumber);

		// read field value
		result = executeCommand(commandReadTable);
		if (failed(result)) {
			return result;
		}

		fieldValue[0] = fieldInfo.bytesToField(commandReadTable.fieldValue,
				charsetName);
		return result;
	}

	@Override
	public int readTableInfo(int tableNumber, Object[] out) throws Exception {
		logger.debug("readTableInfo");
		ReadTableInfo command = new ReadTableInfo();
                command.setPassword(sysPassword);
                command.setTableNumber(tableNumber);

		out[0] = command;
		return executeCommand(command);
	}

	@Override
	public ReadTableInfo readTableInfo(int tableNumber) throws Exception {
		Object[] out = new Object[1];
		check(readTableInfo(tableNumber, out));
		return (ReadTableInfo) out[0];
	}

	@Override
	public PrintCashIn printCashIn(long amount) throws Exception {
		logger.debug("printCashIn");
		PrintCashIn command = new PrintCashIn();
		command.setPassword(usrPassword);
		command.setAmount(amount);
		execute(command);
		return command;
	}

	@Override
	public PrintCashOut printCashOut(long sum) throws Exception {
		logger.debug("printCashOut");
		PrintCashOut command = new PrintCashOut();
                command.setPassword(usrPassword);
                command.setAmount(sum);
		execute(command);
		return command;
	}

	@Override
	public ContinuePrint continuePrint() throws Exception {
		logger.debug("continuePrint");
		ContinuePrint command = new ContinuePrint();
		command.setPassword(usrPassword);
		execute(command);
		return command;
	}

	@Override
	public BeginTest startTest(int periodInMinutes) throws Exception {
		logger.debug("startTest");
		BeginTest command = new BeginTest();
		command.setPassword(usrPassword);
		command.setPeriodInMinutes(periodInMinutes);
		execute(command);
		return command;
	}

	@Override
	public EndTest stopTest() throws Exception {
		logger.debug("stopTest");
		EndTest command = new EndTest();
		command.setPassword(usrPassword);
		execute(command);
		return command;
	}

	@Override
	public VoidFiscalReceipt cancelReceipt() throws Exception {
		logger.debug("cancelReceipt");
		VoidFiscalReceipt command = new VoidFiscalReceipt();
                command.setPassword(usrPassword);
		execute(command);
		return command;
	}

	@Override
	public VoidFiscalReceipt cancelReceipt(int password) throws Exception {
		logger.debug("cancelReceipt");
		VoidFiscalReceipt command = new VoidFiscalReceipt();
                command.setPassword(password);
		execute(command);
		return command;
	}

	@Override
	public EndFiscalReceipt closeReceipt(CloseRecParams params)
			throws Exception {
		logger.debug("closeReceipt");
		EndFiscalReceipt command = new EndFiscalReceipt();
		command.setPassword(usrPassword);
		command.setParams(params);
		int resultCode = executeCommand(command);
		if (resultCode == 168) {
			ReadEJStatus command2 = new ReadEJStatus();
			command2.setPassword(sysPassword);
			int resultCode2 = executeCommand(command2);
			if (resultCode2 == 0) {
				logger.debug("EJ date: "
						+ command2.getStatus().getDocDate().toString());
				logger.debug("EJ time: "
						+ command2.getStatus().getDocTime().toString());
			}
		}
		check(resultCode);
		return command;
	}

	@Override
	public long getSubtotal() throws Exception {
		logger.debug("getSubtotal");
		ReadSubtotal command = new ReadSubtotal(usrPassword);
		execute(command);
		return command.getSum();
	}

	@Override
	public int readOperationRegister(OperationRegister register)
			throws Exception {
		logger.debug("readOperationRegister");
		ReadOperationRegister command = new ReadOperationRegister(usrPassword,
				register.getNumber());
		int result = executeCommand(command);
		if (result == 0) {
			register.setValue(command.getValue());
		}
		return result;
	}

	@Override
	public int readOperationRegister(int number) throws Exception {
		logger.debug("readOperationRegister");
		ReadOperationRegister command = new ReadOperationRegister(usrPassword,
				number);
		execute(command);
		return command.getValue();
	}

	@Override
	public int readCashRegister(CashRegister register) throws Exception {
		logger.debug("readCashRegister");
		ReadCashRegister command = new ReadCashRegister(usrPassword,
				register.getNumber());
		int result = executeCommand(command);
		if (result == 0) {
			register.setValue(command.getValue());
		}
		return result;
	}

	@Override
	public long readCashRegister(int number) throws Exception {
		logger.debug("readCashRegister");
		ReadCashRegister command = new ReadCashRegister(usrPassword, number);
		execute(command);
		return command.getValue();
	}

	@Override
	public PrintEJDayReportOnDates printEJDayReportOnDates(EJDate date1,
			EJDate date2, int reportType) throws Exception {
		logger.debug("printEJDayReportOnDates");
		PrintEJDayReportOnDates command = new PrintEJDayReportOnDates();
                command.setPassword(sysPassword);
                command.setReportType(reportType);
                command.setDate1(date1);
                command.setDate2(date2);
		execute(command);
		return command;
	}

	@Override
	public PrintFMReportDates printFMReportDates(PrinterDate date1,
			PrinterDate date2, int reportType) throws Exception {
		logger.debug("printFMReportDates");
		PrintFMReportDates command = new PrintFMReportDates();
                command.setPassword(taxPassword);
                command.setReportType(reportType);
                command.setDate1(date1);
                command.setDate2(date2);
		execute(command);
		return command;
	}

	@Override
	public PrintEJDayReportOnDays printEJReportDays(int day1, int day2,
			int reportType) throws Exception {
		logger.debug("printEJReportDays");
		PrintEJDayReportOnDays command = new PrintEJDayReportOnDays();
                command.setPassword(sysPassword);
                command.setReportType(reportType);
                command.setDayNumber1(day1);
                command.setDayNumber2(day2);
		execute(command);
		return command;
	}

	@Override
	public PrintFMReportDays printFMReportDays(int day1, int day2,
			int reportType) throws Exception {
		logger.debug("printFMReportDays");
		PrintFMReportDays command = new PrintFMReportDays();
                command.setPassword(taxPassword);
                command.setReportType(reportType);
                command.setSession1(day1);
                command.setSession2(day2);
		execute(command);
		return command;
	}

	@Override
	public void printSale(PriceItem item) throws Exception {
		logger.debug("printSale");
		PrintSale command = new PrintSale(usrPassword, item);
		execute(command);
	}

	@Override
	public void printVoidSale(PriceItem item) throws Exception {
		logger.debug("printVoidSale");

		PrintVoidSale command = new PrintVoidSale(usrPassword, item);
		execute(command);
	}

	@Override
	public void printRefund(PriceItem item) throws Exception {
		logger.debug("printRefund");
		PrintRefund command = new PrintRefund(usrPassword, item);
		execute(command);
	}

	@Override
	public void printVoidRefund(PriceItem item) throws Exception {
		logger.debug("printVoidRefund");

		PrintVoidRefund command = new PrintVoidRefund(usrPassword, item);
		execute(command);
	}

	@Override
	public PrintVoidItem printVoidItem(PriceItem item) throws Exception {
		logger.debug("printVoidItem");
		PrintVoidItem command = new PrintVoidItem();
                command.setPassword(usrPassword);
                command.setItem(item);
		execute(command);
		return command;
	}

	@Override
	public PrintDiscount printDiscount(AmountItem item) throws Exception {
		logger.debug("printDiscount");

		PrintDiscount command = new PrintDiscount();
		command.setPassword(usrPassword);
		command.setItem(item);
		execute(command);
		return command;
	}

	@Override
	public PrintVoidDiscount printVoidDiscount(AmountItem item)
			throws Exception {
		logger.debug("printVoidDiscount");

		PrintVoidDiscount command = new PrintVoidDiscount();
                command.setPassword(usrPassword);
                command.setItem(item);
		execute(command);
		return command;
	}

	@Override
	public PrintCharge printCharge(AmountItem item) throws Exception {
		logger.debug("printCharge");

		PrintCharge command = new PrintCharge();
		command.setPassword(usrPassword);
		command.setItem(item);
		execute(command);
		return command;
	}

	@Override
	public PrintVoidCharge printVoidCharge(AmountItem item) throws Exception {
		logger.debug("printVoidCharge");
		PrintVoidCharge command = new PrintVoidCharge();
                command.setPassword(usrPassword);
                command.setItem(item);
		execute(command);
		return command;
	}

	@Override
	public ReadFMLastRecordDate readFMLastRecordDate() throws Exception {
		logger.debug("getLastFmRecordDate");

		ReadFMLastRecordDate command = new ReadFMLastRecordDate();
		command.setPassword(sysPassword);
		execute(command);
		return command;
	}

	@Override
	public PrintXReport printXReport() throws Exception {
		logger.debug("printXReport");

		PrintXReport command = new PrintXReport();
                command.setPassword(sysPassword);
		execute(command);
		return command;
	}

	@Override
	public PrintZReport printZReport() throws Exception {
		logger.debug("printZReport");

		PrintZReport command = new PrintZReport();
                command.setPassword(sysPassword);
		execute(command);
		return command;
	}

	@Override
	public int printDepartmentReport() throws Exception {
		logger.debug("printDepartmentReport");
		PrintDepartmentReport command = new PrintDepartmentReport();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int printTaxReport() throws Exception {
		logger.debug("printTaxReport");
		PrintTaxReport command = new PrintTaxReport(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int printTotalizers() throws Exception {
		logger.debug("printTotalizers");
		PrintTotalizers command = new PrintTotalizers(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int bufferZReport() throws Exception {
		logger.debug("bufferZReport");
		BufferZReport command = new BufferZReport();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int printBufferedZReport() throws Exception {
		logger.debug("printBufferedZReport");
		PrintBufferedZReport command = new PrintBufferedZReport();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int printHeader() throws Exception {
		logger.debug("printHeader");
		PrintHeader command = new PrintHeader(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int printTrailer() throws Exception {
		logger.debug("printTrailer");
		PrintTrailer command = new PrintTrailer(sysPassword);
		return executeCommand(command);
	}

	@Override
	public int writeDate(PrinterDate date) throws Exception {
		logger.debug("writeDate");

		SetDateCommand command = new SetDateCommand(sysPassword, date);
		return executeCommand(command);
	}

	@Override
	public int confirmDate(PrinterDate date) throws Exception {
		logger.debug("confirmDate");

		ConfirmDate command = new ConfirmDate();
		command.setPassword(sysPassword);
		command.setDate(date);
		return executeCommand(command);
	}

	@Override
	public void writeTime(PrinterTime time) throws Exception {
		logger.debug("setTime");

		WriteTime command = new WriteTime(sysPassword, time);
		execute(command);
	}

	@Override
	public void writePortParams(int portNumber, int baudRate, int timeout)
			throws Exception {
		logger.debug("writePortParams(" + String.valueOf(portNumber) + ", "
				+ String.valueOf(baudRate) + ", " + String.valueOf(timeout)
				+ ")");

		MethodParameter.checkByte(portNumber, "portNumber");
		MethodParameter.checkByte(baudRate, "baudRate");
		WritePortParams command = new WritePortParams(sysPassword, portNumber,
				baudRate, timeout);
		execute(command);
		Thread.sleep(300);
	}

	@Override
	public void printBarcode(String barcode) throws Exception {
		logger.debug("printBarcode");

		PrintBarcode command = new PrintBarcode();
		command.setPassword(usrPassword);
		command.setBarcode(barcode);
		execute(command);
	}

	@Override
	public void duplicateReceipt() throws Exception {
		logger.debug("duplicateReceipt");

		PrintReceiptCopy command = new PrintReceiptCopy(usrPassword);
		execute(command);
	}

	@Override
	public OpenReceipt openReceipt(int receiptType) throws Exception {
		logger.debug("openReceipt");

		OpenReceipt command = new OpenReceipt();
		command.setPassword(usrPassword);
		command.setReceiptType(receiptType);
		execute(command);
		return command;
	}

	@Override
	public int loadGraphics(int lineNumber, byte[] data) throws Exception {
		logger.debug("loadGraphics, " + String.valueOf(lineNumber));
		MethodParameter.checkRange(lineNumber, 0, 255, "lineNumber");

		LoadGraphics command = new LoadGraphics();
		command.setPassword(usrPassword);
		command.setLineNumber(lineNumber);
		command.setData(data);
		return executeCommand(command);
	}

	@Override
	public int loadGraphicsEx(int lineNumber, byte[] data) throws Exception {
		logger.debug("loadExtendedGraphics, " + String.valueOf(lineNumber));
		LoadExtendedGraphics command = new LoadExtendedGraphics();
		command.setPassword(usrPassword);
		command.setLineNumber(lineNumber);
		command.setData(data);
		return executeCommand(command);
	}

	@Override
	public int printGraphics(int line1, int line2) throws Exception {
		logger.debug("printGraphics(" + String.valueOf(line1) + ", "
				+ String.valueOf(line2) + ")");

		PrintGraphics command = new PrintGraphics(usrPassword, line1, line2);
		return executeCommand(command);
	}

	@Override
	public int printGraphicsEx(int line1, int line2) throws Exception {
		logger.debug("printGraphicsEx(" + String.valueOf(line1) + ", "
				+ String.valueOf(line2) + ")");

		PrintGraphicsEx command = new PrintGraphicsEx(usrPassword, line1, line2);
		return executeCommand(command);
	}

	@Override
	public void endDump() throws Exception {
		logger.debug("endDump");
		EndDump command = new EndDump();
		command.setPassword(usrPassword);
		execute(command);
	}

	@Override
	public void printGraphicLine(int height, byte[] data) throws Exception {
		logger.debug("printGraphicLine");

		PrintGraphicLine command = new PrintGraphicLine(usrPassword, height,
				data);
		execute(command);
		sleep(params.graphicsLineDelay);
	}

	// CutPaper

	@Override
	public int cutPaper(int cutType) throws Exception {
		logger.debug("cutReceipt");

		CutPaper command = new CutPaper();
		command.setPassword(usrPassword);
		command.setCutType(cutType);
		return executeCommand(command);
	}

	@Override
	public void openCashDrawer(int drawerNumber) throws Exception {
		logger.debug("openDrawer");
		OpenCashDrawer command = new OpenCashDrawer();
		command.setPassword(usrPassword);
		command.setDrawerNumber(drawerNumber);
		execute(command);
	}

	@Override
	public boolean checkEcrMode(int mode) throws Exception {
		logger.debug("checkEcrMode");
		switch (mode) {
		case MODE_FULLREPORT:
		case MODE_EJREPORT:
		case MODE_SLPPRINT:
			Thread.sleep(TimeToSleep);
			break;

		default:
			return true;
		}
		return false;
	}

	@Override
	public PrinterStatus waitForPrinting() throws Exception {
		PrinterStatus status = null;
		try {
			for (;;) {
				status = readPrinterStatus();
				switch (status.getSubmode()) {
				case ECR_SUBMODE_IDLE: {
					if (checkEcrMode(status.getMode())) {
						return status;
					}
					break;
				}

				case ECR_SUBMODE_PASSIVE:
				case ECR_SUBMODE_ACTIVE: {
					checkPaper(status);
					break;
				}

				case ECR_SUBMODE_AFTER: {
					continuePrint();
					break;
				}

				case ECR_SUBMODE_REPORT:
				case ECR_SUBMODE_PRINT: {
					Thread.sleep(TimeToSleep);
					break;
				}

				default: {
					logger.debug("Unknown submode");
					return status;
				}
				}
			}
		} catch (InterruptedException e) {
			// Restore the interrupted status
			logger.error("InterruptedException", e);
			Thread.currentThread().interrupt();
		}
		return status;
	}

	@Override
	public int[] getSupportedBaudRates() throws Exception {
		return getModel().getSupportedBaudRates();
	}

	@Override
	public boolean tryCancelReceipt(int password) throws Exception {
		VoidFiscalReceipt command = new VoidFiscalReceipt();
                command.setPassword(password);
		if (executeCommand(command) == 0x59) {
			return false;
		} else {
			check(command.getResultCode());
			return true;
		}
	}

	@Override
	public void writeDecimalPoint(int position) throws Exception {

		WriteDecimalPoint command = new WriteDecimalPoint(sysPassword, position);
		execute(command);
	}

	@Override
	public void beginFiscalDay() throws Exception {
		BeginFiscalDay command = new BeginFiscalDay();
		command.setPassword(usrPassword);
		execute(command);
	}

	@Override
	public void resetFM() throws Exception {
		ResetFM command = new ResetFM();
		execute(command);
	}

	@Override
	public void sysAdminCancelReceipt() throws Exception {
		logger.debug("sysAdminCancelReceipt");
		String[] passwordString = new String[0];
		// try use known passwords
		if (tryCancelReceipt(usrPassword)) {
			return;
		}
		if (tryCancelReceipt(sysPassword)) {
			return;
		}
		// reading passwords from tables
		for (int i = 1; i < 30; i++) {
			check(readTable(2, i, 1, passwordString));
			int password = Integer.parseInt(passwordString[0]);
			if (tryCancelReceipt(password)) {
				return;
			}
		}
		throw new Exception(Localizer.getString(Localizer.FailedCancelReceipt));
	}

	@Override
	public int getBaudRateIndex(int value) throws Exception {
		int[] deviceBaudRates = getSupportedBaudRates();
		for (byte i = 0; i < deviceBaudRates.length; i++) {
			if (value == deviceBaudRates[i]) {
				return i;
			}
		}
		return deviceBaudRates.length - 1;
	}

	@Override
	public void setBaudRate(int baudRate) throws Exception {
		port.setBaudRate(baudRate);
	}

	@Override
	public boolean connectDevice(int baudRate, int deviceBaudRate,
			int deviceByteTimeout) throws Exception {
		setBaudRate(baudRate);
		ReadLongStatus cmd = readLongStatus();
		cmd.checkResultCode();
		writePortParams(cmd.getStatus().getPortNumber(),
				getBaudRateIndex(deviceBaudRate), deviceByteTimeout);
		setBaudRate(baudRate);
		return true;
	}

	@Override
	public void checkBaudRate(int value) throws Exception {
		int[] deviceBaudRates = getSupportedBaudRates();
		for (int i = 0; i < deviceBaudRates.length; i++) {
			if (value == deviceBaudRates[i]) {
				return;
			}
		}
		throw new Exception(Localizer.getString(Localizer.BaudrateNotSupported));
	}

	@Override
	public void closePort() throws Exception {
		port.close();
	}

	@Override
	public void writeTables(PrinterTables tables) throws Exception {
		for (int i = 0; i < tables.size(); i++) {
			PrinterTable table = tables.get(i);
			PrinterFields fields = table.getFields();
			for (int j = 0; j < fields.size(); j++) {
				PrinterField field = fields.get(j);
				check(writeTable(field.getTable(), field.getRow(),
						field.getNumber(), field.getValue()));
			}
		}
	}

	@Override
	public void writeFields(PrinterFields fields) throws Exception {
		String[] fieldValue = new String[1];
		for (int i = 0; i < fields.size(); i++) {
			fieldValue[0] = "";
			PrinterField field = fields.get(i);
			check(readTable(field.getTable(), field.getRow(),
					field.getNumber(), fieldValue));

			if (fieldValue[0].compareTo(field.getValue()) != 0) {
				check(writeTable(field.getTable(), field.getRow(),
						field.getNumber(), field.getValue()));
			}
		}
	}

	@Override
	public void updateTableInfo(int tableNumber) throws Exception {
		if (tableNumber != tableInfo.getTableNumber()) {
			tableInfo = readTableInfo(tableNumber);
		}
	}

	@Override
	public boolean isValidField(int tableNumber, int rowNumber, int fieldNumber)
			throws Exception {
		updateTableInfo(tableNumber);
		return (rowNumber >= 1) && (rowNumber <= tableInfo.getRowCount())
				&& (fieldNumber >= 1)
				&& (fieldNumber <= tableInfo.getFieldCount());
	}

	@Override
	public void readTable(PrinterTable table) throws Exception {
		for (int rowNumber = 1; rowNumber <= table.getRowCount(); rowNumber++) {
			for (int fieldNumber = 1; fieldNumber <= table.getFieldCount(); fieldNumber++) {
				String[] fieldValue = new String[1];
				check(readTable(table.getNumber(), rowNumber, fieldNumber,
						fieldValue));
				PrinterField field = new PrinterField(table.getNumber(),
						rowNumber, fieldNumber, fieldInfo.getFieldSize(),
						fieldInfo.getFieldType(), fieldInfo.getMinValue(),
						fieldInfo.getMaxValue(), fieldInfo.getFieldName());
				field.setValue(fieldValue[0]);
				table.getFields().add(field);
			}
		}
	}

	@Override
	public void readField(PrinterField field) throws Exception {
		String[] fieldValue = new String[1];
		check(readTable(field.getTable(), field.getRow(), field.getNumber(),
				fieldValue));
		field.setValue(fieldValue[0]);
	}

	@Override
	public void writeField(PrinterField field) throws Exception {
		check(writeTable(field.getTable(), field.getRow(), field.getNumber(),
				field.getValue()));
	}

	@Override
	public void readTables(PrinterTables tables) throws Exception {
		tables.clear();
		int tableNumber = 1;
		while (true) {
			ReadTableInfo command = new ReadTableInfo();
                        command.setPassword(sysPassword);
                        command.setTableNumber(tableNumber);
			int result = executeCommand(command);
			if (result == SMFP_EFPTR_INVALID_TABLE) {
				break;
			}
			check(result);

			tableInfo = command;

			PrinterTable table = new PrinterTable(tableInfo.getTableNumber(),
					tableInfo.getTableName(), tableInfo.getRowCount(),
					tableInfo.getFieldCount());
			tables.add(table);

			for (int fieldNumber = 1; fieldNumber <= table.getFieldCount(); fieldNumber++) {
				for (int rowNumber = 1; rowNumber <= table.getRowCount(); rowNumber++) {
					String[] fieldValue = new String[1];
					check(readTable(tableNumber, rowNumber, fieldNumber,
							fieldValue));
					PrinterField field = new PrinterField(tableNumber,
							rowNumber, fieldNumber, fieldInfo.getFieldSize(),
							fieldInfo.getFieldType(), fieldInfo.getMinValue(),
							fieldInfo.getMaxValue(), fieldInfo.getFieldName());
					field.setValue(fieldValue[0]);
					table.getFields().add(field);
				}
			}
			tableNumber++;
		}
	}

	@Override
	public PrinterStatus readShortPrinterStatus() throws Exception {
		ShortPrinterStatus status = readShortStatus();
		int mode = status.getMode();
		int submode = status.getSubmode();
		int flags = status.getFlags();
		int operatorNumber = status.getOperatorNumber();

		loggerDebugMode(mode, submode);
                
                PrinterStatus printerStatus = new PrinterStatus();
                printerStatus.setMode(mode);
                printerStatus.setSubmode(submode);
                printerStatus.setFlags(flags);
                printerStatus.setOperator(operatorNumber);
		return printerStatus;
	}

	@Override
	public PrinterStatus readLongPrinterStatus() throws Exception {
		readLongStatus().checkResultCode();
		LongPrinterStatus status = getLongStatus();
		int mode = status.getMode();
		int submode = status.getSubmode();
		int flags = status.getFlags();
		int operatorNumber = status.getOperatorNumber();

		loggerDebugMode(mode, submode);
                
                PrinterStatus printerStatus = new PrinterStatus();
                printerStatus.setMode(mode);
                printerStatus.setSubmode(submode);
                printerStatus.setFlags(flags);
                printerStatus.setOperator(operatorNumber);
		return printerStatus;
	}

	private void loggerDebugMode(int mode, int subMode) {
		logger.debug("Mode: 0x" + Hex.toHex(mode) + ", "
				+ PrinterMode.getText(mode) + ". Flags: 0x"
				+ Hex.toHex(subMode));

		logger.debug("Submode: " + String.valueOf(subMode) + ", "
				+ PrinterSubmode.getText(subMode));
	}

	@Override
	public PrinterStatus readPrinterStatus() throws Exception {
		PrinterStatus status;
		switch (params.statusCommand) {
		case SMFP_STATUS_COMMAND_DS:
			if (getModel().getCapShortStatus()) {
				status = readShortPrinterStatus();
			} else {
				status = readLongPrinterStatus();
			}
			break;

		case SMFP_STATUS_COMMAND_10H:
			status = readShortPrinterStatus();
			break;

		default:
			status = readLongPrinterStatus();
			break;
		}
		for (int i = 0; i < events.size(); i++) {
			((IPrinterEvents) events.get(i)).printerStatusRead(status);
		}
		return status;
	}

	@Override
	public DeviceMetrics getDeviceMetrics() {
		return deviceMetrics;
	}

	@Override
	public int readDeviceMetrics() throws Exception {
		ReadDeviceMetrics command = new ReadDeviceMetrics();
		int result = executeCommand(command);
		if (result == 0) {
			deviceMetrics = command.getDeviceMetrics();
		}
		return result;
	}

	@Override
	public PrinterModel getModel() throws Exception {
		return model;
	}

	@Override
	public void updateModel() throws Exception 
        {
		model = selectPrinterModel(getDeviceMetrics());
                readFonts();
	}

	public PrinterModel selectPrinterModel(DeviceMetrics metrics)
			throws Exception {
		PrinterModel result = models.find(metrics.getModel(),
				metrics.getProtocolVersion(), metrics.getProtocolSubVersion());
		if (result == null) {
			result = models.itemByID(SMFP_MODELID_DEFAULT);
		}
		if (result == null) {
			throw new Exception("Printer model not found");
		}
		logger.debug("Selected model: " + result.getName());
		return result;
	}

	@Override
	public boolean getWrapText() {
		return wrapText;
	}

	@Override
	public void setWrapText(boolean value) {
		wrapText = value;
	}

	@Override
	public void checkPaper(PrinterStatus status) throws Exception {
		int resultCode;
		if (getModel().getCapRecPresent()
				&& status.getPrinterFlags().isRecEmpty()) {
			resultCode = SMFP_EFPTR_NO_REC_PAPER;
			throw new SmFiscalPrinterException(resultCode,
					PrinterError.getFullText(resultCode));
		}
		if (getModel().getCapJrnPresent()
				&& status.getPrinterFlags().isJrnEmpty()) {
			resultCode = SMFP_EFPTR_NO_JRN_PAPER;
			throw new SmFiscalPrinterException(resultCode,
					PrinterError.getFullText(resultCode));
		}
	}

	@Override
	public int initTables() throws Exception {
		InitTables command = new InitTables();
		command.setPassword(sysPassword);
		return executeCommand(command);
	}

	@Override
	public void loadImageData(int lineNumber, byte[] data) throws Exception {
		if (getModel().getCapGraphicsEx()) {
			loadGraphicsEx(lineNumber, data);
		} else {
			if (getModel().getCapGraphics()) {
				loadGraphics(lineNumber, data);
			}
		}
	}

	@Override
	public void printImage(int line1, int line2) throws Exception {
		if (getModel().getCapGraphicsEx()) {
			printGraphicsEx(line1, line2);
		} else {
			printGraphics(line1, line2);
		}
	}

	public static int boolToInt(boolean value) {
		if (value) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void writeParameter(int parameterID, boolean value) throws Exception {
		writeParameter(parameterID, boolToInt(value));
	}

	@Override
	public void writeParameter(int parameterID, String value) throws Exception {
		logger.debug("writeParameter(" + String.valueOf(parameterID) + ", "
				+ String.valueOf(value) + ")");

		PrinterParameter parameter = getModel().getParameter(parameterID);
		if (parameter == null) {
			logger.error("Parameter not found, ID="
					+ String.valueOf(parameterID));
			return;
		}

		check(writeTable(parameter.getTableNumber(), parameter.getRowNumber(),
				parameter.getFieldNumber(), value));
	}

	@Override
	public void writeParameter(int parameterID, int value) throws Exception {
		logger.debug("writeParameter(" + String.valueOf(parameterID) + ", "
				+ String.valueOf(value) + ")");

		PrinterParameter parameter = getModel().getParameter(parameterID);
		if (parameter == null) {
			logger.error("Parameter not found, ID="
					+ String.valueOf(parameterID));
			return;
		}
		int fieldValue = parameter.getFieldValue(value);
		check(writeTable(parameter.getTableNumber(), parameter.getRowNumber(),
				parameter.getFieldNumber(), String.valueOf(fieldValue)));
	}

	@Override
	public String readParameter(int parameterID) throws Exception {

		logger.debug("readParameter(" + String.valueOf(parameterID) + ")");

		PrinterParameter parameter = getModel().getParameter(parameterID);
		if (parameter == null) {
			throw new Exception("Parameter not found, ID="
					+ String.valueOf(parameterID));
		}

		String[] fieldValue = new String[1];
		check(readTable(parameter.getTableNumber(), parameter.getRowNumber(),
				parameter.getFieldNumber(), fieldValue));
		return fieldValue[0];
	}

	@Override
	public int readIntParameter(int parameterID) throws Exception {
		return Integer.parseInt(readParameter(parameterID));
	}

	@Override
	public void printBarcode(PrinterBarcode barcode) throws Exception {
		logger.debug("printBarcode");
		if (barcode.getHeight() <= 0) {
			throw new Exception(
					Localizer.getString(Localizer.InvalidBarcodeHeight));
		}

		switch (barcode.getPrintType()) {
		case SmFptrConst.SMFPTR_PRINTTYPE_DEVICE:
			printBarcodeDevice(barcode);
			break;

		case SmFptrConst.SMFPTR_PRINTTYPE_DRIVER:
			printBarcodeDriver(barcode);
			break;

		default:
			throw new Exception(
					Localizer.getString(Localizer.InvalidBarcodePrintType));
		}
	}

	private void printBarcodeDevice(PrinterBarcode barcode) throws Exception {
		if (getModel().getCapPrintBarcode2()) {
			printBarcode2(barcode);
		} else {
			// only EAN-13 barcode
			if (barcode.getType() != PrinterBarcode.SM_BARCODE_EAN13) {
				throw new Exception(
						Localizer
								.getString(Localizer.PrinterSupportesEAN13Only));
			}

			if (barcode.isTextAbove()) {
				printBarcodeLabel(barcode);
			}
			printBarcode(barcode.getData());
		}
	}

	public void printBarcode2(PrinterBarcode barcode) throws Exception {
		logger.debug("printBarcode2");
		PrintBarcode2 command = new PrintBarcode2();
		command.setOperatorPassword(usrPassword);
		command.setBarcodeHeight(barcode.getHeight());
		command.setBarWidth(barcode.getBarWidth());
		command.setBarcodeHRIPosition(barcode.getTextPosition());
		command.setBarcodeHRIPitch(barcode.getTextFont());
		command.setBarcodeType(barcode.getType());
		command.setBarcodeData(barcode.getData());
		execute(command);
	}

	private void printBarcodeLabel(PrinterBarcode barcode) throws Exception {
		logger.debug("printBarcodeLabel");
		String data = centerLine(barcode.getLabel());
		doPrintText(SMFP_STATION_REC, data, params.font);
	}

	@Override
	public void printText(int station, String text, FontNumber font)
			throws Exception {
		logger.debug("printText(" + station + ", " + text + ")");
		CommandContext context = new CommandContext();
		context.logoPosition = SmFptrConst.SMFPTR_LOGO_NONE;
		String data = processEscCommands(text, context);
		if (data.length() > 0) {
			doPrintText(station, data, font);
		}
	}

	public void doPrintText(int station, String text, FontNumber font)
			throws Exception {
		logger.debug("doPrintText(" + station + ", " + text + ")");
		String[] lines = splitText(text, font);
		for (int i = 0; i < lines.length; i++) {
			printLine(station, lines[i], font);
		}
	}

	@Override
	public String processEscCommands(String text, CommandContext context)
			throws Exception {
		if (params.escCommandsEnabled) {
			text = escPrinter.parse(text, params.stringEncoding);
			int commandCount = escPrinter.getCommands().size();
			escPrinter.execute(context);
		}
		return text;
	}

	public String centerLine(String data) throws Exception {
		return StringUtils.centerLine(data, getMessageLength());
	}

	private void printBarcodeDriver(PrinterBarcode barcode) throws Exception {
		logger.debug("printBarcodeDriver");
		byte[] data = null;
		SmBarcodeEncoder encoder = BarcodeEncoderFactory.getBarcodeEncoder();

		if (barcode.isTextAbove()) {
			printBarcodeLabel(barcode);
		}
		if (barcode.isLinear() && getModel().getCapPrintGraphicsLine()) {
			data = encoder.encode(barcode, getModel().getPrintWidth());
			printGraphicLine(barcode.getHeight(), data);
			waitForPrinting();
		} else {
			data = encoder.encode(barcode, getMaxGraphicsWidth());
			printBarcodeGraphics(data, barcode);
		}
		if (barcode.isTextBelow()) {
			printBarcodeLabel(barcode);
		}
	}

	public int getMessageLength() throws Exception {
		return getModel().getTextLength(params.font);
	}

	@Override
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Restore the interrupted status
			logger.error("InterruptedException", e);
			Thread.currentThread().interrupt();
		}
	}

	public int getMaxGraphicsWidth() throws Exception {
		return getModel().getMaxGraphicsWidth();
	}

	// use area after images to print barcode
	private void printBarcodeGraphics(byte[] data, PrinterBarcode barcode)
			throws Exception {
		int firstLine = printerImages.getTotalSize();
		int height = firstLine + barcode.getHeight();
		if (height > getMaxGraphicsHeight()) {
			throw new Exception(
					Localizer.getString(Localizer.InvalidImageHeight) + ", "
							+ String.valueOf(height) + " > "
							+ String.valueOf(getMaxGraphicsHeight()));
		}

		for (int i = 1; i <= barcode.getHeight(); i++) {
			loadGraphics(firstLine + i, barcode.getHeight(), data);
		}
		printGraphics(firstLine + 1, firstLine + barcode.getHeight());
	}

	public int getMaxGraphicsHeight() throws Exception {
		return getModel().getMaxGraphicsHeight();
	}

	public void loadGraphics(int lineNumber, int lineCount, byte[] data)
			throws Exception {
		if ((lineNumber + lineCount) > getMaxGraphicsHeight()) {
			throw new Exception(
					Localizer.getString(Localizer.InvalidImageHeight) + ", "
							+ String.valueOf(lineNumber + lineCount) + " > "
							+ String.valueOf(getMaxGraphicsHeight()));
		}

		if (getModel().getCapGraphicsEx()) {
			loadGraphicsEx(lineNumber, data);
		} else {
			if (getModel().getCapGraphics()) {
				loadGraphics(lineNumber, data);
			}
		}
	}

	public int readIntParameter(PrinterParameter parameter) throws Exception {
		String[] fieldValue = new String[1];
		check(readTable(parameter.getTableNumber(), parameter.getRowNumber(),
				parameter.getFieldNumber(), fieldValue));
		return Integer.parseInt(fieldValue[0]);
	}

	@Override
	public int getLineSpacing() throws Exception {
		if (getModel().getCapParameter(SMFP_PARAMID_LINE_SPACING)) {
			PrinterParameter parameter = getModel().getParameter(
					SMFP_PARAMID_LINE_SPACING);
			return readIntParameter(parameter);
		} else {
			return getModel().getLineSpacing();
		}
	}

	@Override
	public int getLineHeight(FontNumber font) throws Exception {
		return getModel().getFontHeight(font) - getModel().getLineSpacing()
				+ getLineSpacing();
	}

	@Override
	public void checkImageSize(int firstLine, int imageWidth, int imageHeight)
			throws Exception {
		// check max image width
		int maxGraphicsWidth = getModel().getMaxGraphicsWidth();
		if (imageWidth > maxGraphicsWidth) {
			throw new Exception(
					Localizer.getString(Localizer.InvalidImageWidth) + ", "
							+ String.valueOf(imageWidth) + " > "
							+ String.valueOf(maxGraphicsWidth));
		}
		// check max image height

		int height = firstLine + imageHeight;
		int maxImageHeight = getModel().getMaxGraphicsHeight();
		if (height > maxImageHeight) {
			throw new Exception(
					Localizer.getString(Localizer.InvalidImageHeight) + ", "
							+ String.valueOf(height) + " > "
							+ String.valueOf(maxImageHeight));
		}
	}

	@Override
	public int readLicense(String[] license) throws Exception {
		ReadLicense command = new ReadLicense(sysPassword);
		int result = executeCommand(command);
		if (result == 0) {
			license[0] = String.valueOf(command.getLicense());
		}
		return result;
	}

	@Override
	public void printSeparator(int separatorType, int height) throws Exception {
		printGraphicLine(height, getSeparatorData(separatorType));
		waitForPrinting();
	}

	public byte[] getSeparatorData(int separatorType) throws Exception {
		byte[] SEPARATOR_PATTERN_BLACK = { (byte) 0xFF };
		byte[] SEPARATOR_PATTERN_WHITE = { 0x00 };
		byte[] SEPARATOR_PATTERN_DOTTED_1 = { (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, 0x00, 0x00, 0x00 };
		byte[] SEPARATOR_PATTERN_DOTTED_2 = { (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00 };
		byte[] pattern;

		switch (separatorType) {
		case SmFptrConst.SMFPTR_SEPARATOR_BLACK:
			pattern = SEPARATOR_PATTERN_BLACK;
			break;

		case SmFptrConst.SMFPTR_SEPARATOR_WHITE:
			pattern = SEPARATOR_PATTERN_WHITE;
			break;

		case SmFptrConst.SMFPTR_SEPARATOR_DOTTED_1:
			pattern = SEPARATOR_PATTERN_DOTTED_1;
			break;

		case SmFptrConst.SMFPTR_SEPARATOR_DOTTED_2:
			pattern = SEPARATOR_PATTERN_DOTTED_2;
			break;

		default:
			pattern = SEPARATOR_PATTERN_BLACK;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int len = getModel().getPrintWidth() / 8;
		while (stream.size() < len) {
			stream.write(pattern);
		}
		return stream.toByteArray();
	}

	@Override
	public int getPrintWidth() throws Exception {
		return getModel().getPrintWidth();
	}

	@Override
	public FlexCommands getCommands() throws Exception {
		if (commands == null) {
			commands = new FlexCommands();
			FlexCommandsReader reader = new FlexCommandsReader();
			reader.load(commands);
		}
		return commands;
	}

	@Override
	public int getCommandTimeout(int code) throws Exception {
		int timeout;
		FlexCommand command = getCommands().itemByCode(code);
		if (command != null) {
			timeout = command.getTimeout();
		} else {
			timeout = PrinterCommand.getDefaultTimeout(code);
		}
		return timeout;
	}

	@Override
	public ReadEJActivationReport readEJActivationReport() throws Exception {
		ReadEJActivationReport command = new ReadEJActivationReport();
		command.setPassword(sysPassword);
		executeCommand(command);
		return command;
	}

	@Override
	public ReadFMTotals readFMTotals(int mode) throws Exception {
		ReadFMTotals command = new ReadFMTotals();
		command.setPassword(sysPassword);
		command.setMode((byte) mode);
		executeCommand(command);
		return command;
	}

	public ReadEJDocumentLine readEJDocumentLine() throws Exception {
		ReadEJDocumentLine command = new ReadEJDocumentLine();
		command.setPassword(sysPassword);
		executeCommand(command);
		return command;
	}

	@Override
	public String[] readEJActivationText(int maxCount) throws Exception {
		Vector lines = new Vector();
		check(cancelEJDocument());
		if (resultCode == 0) {
			ReadEJActivationReport command1 = readEJActivationReport();
			if (command1.getResultCode() == 0) {
				lines.add(command1.getEcrModel());
				for (int i = 0; i < maxCount; i++) {
					ReadEJDocumentLine command2 = readEJDocumentLine();
					if (command2.isFailed()) {
						break;
					}
					lines.add(command2.getData());
				}
			}
			check(cancelEJDocument());
		}
		return (String[]) (lines.toArray(new String[0]));
	}
        
        public void readFonts() throws Exception
        {
            PrinterFonts fonts = new PrinterFonts();
            int fontNumber = 1;
            int fontCount = 7;
            while (fontNumber <= fontCount)
            {
                ReadFontMetrics command = new ReadFontMetrics();
                command.setPassword(sysPassword);
                command.setFont(fontNumber);
                if (executeCommand(command) != 0) break;
                fonts.add(fontNumber, command.getCharWidth(), command.getCharHeight());
                fontNumber++;
                fontCount = command.getFontCount();
            }
            if (fonts.size() > 0){
                getModel().setFonts(fonts);
            }
        }
        
        
        
}
