/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter;

import org.apache.log4j.Logger;

import com.shtrih.barcode.PrinterBarcode;
import com.shtrih.ej.EJDate;
import com.shtrih.fiscalprinter.command.AmountItem;
import com.shtrih.fiscalprinter.command.Beep;
import com.shtrih.fiscalprinter.command.BeginTest;
import com.shtrih.fiscalprinter.command.CashRegister;
import com.shtrih.fiscalprinter.command.CloseRecParams;
import com.shtrih.fiscalprinter.command.ContinuePrint;
import com.shtrih.fiscalprinter.command.DeviceMetrics;
import com.shtrih.fiscalprinter.command.EndFiscalReceipt;
import com.shtrih.fiscalprinter.command.EndTest;
import com.shtrih.fiscalprinter.command.FlexCommands;
import com.shtrih.fiscalprinter.command.IPrinterEvents;
import com.shtrih.fiscalprinter.command.LongPrinterStatus;
import com.shtrih.fiscalprinter.command.OpenReceipt;
import com.shtrih.fiscalprinter.command.OperationRegister;
import com.shtrih.fiscalprinter.command.PriceItem;
import com.shtrih.fiscalprinter.command.PrintCashIn;
import com.shtrih.fiscalprinter.command.PrintCashOut;
import com.shtrih.fiscalprinter.command.PrintCharge;
import com.shtrih.fiscalprinter.command.PrintDiscount;
import com.shtrih.fiscalprinter.command.PrintEJDayReportOnDates;
import com.shtrih.fiscalprinter.command.PrintEJDayReportOnDays;
import com.shtrih.fiscalprinter.command.PrintFMReportDates;
import com.shtrih.fiscalprinter.command.PrintFMReportDays;
import com.shtrih.fiscalprinter.command.PrintVoidCharge;
import com.shtrih.fiscalprinter.command.PrintVoidDiscount;
import com.shtrih.fiscalprinter.command.PrintVoidItem;
import com.shtrih.fiscalprinter.command.PrintXReport;
import com.shtrih.fiscalprinter.command.PrintZReport;
import com.shtrih.fiscalprinter.command.PrinterCommand;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.command.PrinterDate;
import com.shtrih.fiscalprinter.command.PrinterStatus;
import com.shtrih.fiscalprinter.command.PrinterTime;
import com.shtrih.fiscalprinter.command.ReadEJActivationReport;
import com.shtrih.fiscalprinter.command.ReadEJStatus;
import com.shtrih.fiscalprinter.command.ReadFMLastRecordDate;
import com.shtrih.fiscalprinter.command.ReadFMTotals;
import com.shtrih.fiscalprinter.command.ReadFieldInfo;
import com.shtrih.fiscalprinter.command.ReadLongStatus;
import com.shtrih.fiscalprinter.command.ReadTableInfo;
import com.shtrih.fiscalprinter.command.ShortPrinterStatus;
import com.shtrih.fiscalprinter.command.VoidFiscalReceipt;
import com.shtrih.fiscalprinter.model.PrinterModel;
import com.shtrih.fiscalprinter.model.PrinterModels;
import com.shtrih.fiscalprinter.port.PrinterPort;
import com.shtrih.fiscalprinter.table.PrinterField;
import com.shtrih.fiscalprinter.table.PrinterFields;
import com.shtrih.fiscalprinter.table.PrinterTable;
import com.shtrih.fiscalprinter.table.PrinterTables;
import com.shtrih.jpos.fiscalprinter.FptrParameters;
import com.shtrih.jpos.fiscalprinter.PrinterImage;
import com.shtrih.jpos.fiscalprinter.PrinterImages;
import com.shtrih.jpos.fiscalprinter.ReceiptImages;
import com.shtrih.printer.ncr7167.CommandContext;
import com.shtrih.printer.ncr7167.NCR7167Printer;

/**
 * 
 * @author Kravtsov
 */
public class SMFiscalPrinterNull implements SMFiscalPrinter {

	public PrinterProtocol device;
	private PrinterModel model = null;
	private final PrinterModels models = new PrinterModels();
	private final DeviceMetrics deviceMetrics = new DeviceMetrics();
	private final PrinterStatus printerStatus = new PrinterStatus();
	private final LongPrinterStatus longStatus = new LongPrinterStatus();
	private final ShortPrinterStatus shortStatus = new ShortPrinterStatus();
	public ReadTableInfo tableInfo = new ReadTableInfo();
	public ReadFieldInfo fieldInfo = new ReadFieldInfo();
	public static Logger logger = Logger.getLogger(SMFiscalPrinterNull.class);
	private final PrinterPort port;
	private final FptrParameters params;
	private final PrinterImages printerImages = new PrinterImages();
	private final int resultCode = 0;

	public SMFiscalPrinterNull(PrinterPort port, PrinterProtocol device,
			FptrParameters params) {
		this.port = port;
		this.device = device;
		this.params = params;
		models.load();
		try {
			model = models.itemByID(PrinterConst.SMFP_MODELID_DEFAULT);
		} catch (Exception e) {
			logger.error(e);
		}
		// 0000 0011 1111 1111
		int flags = 0x03FF;
		printerStatus.setFlags(flags);
		shortStatus.setFlags(flags);
		longStatus.setFlags(flags);

		int mode = PrinterConst.ECRMODE_24NOTOVER;
		printerStatus.setMode(mode);
		longStatus.setMode(mode);
		shortStatus.setMode(mode);

		int submode = 0;
		printerStatus.setSubmode(submode);
		shortStatus.setSubmode(submode);
		longStatus.setSubmode(submode);

	}

	@Override
	public FptrParameters getParams() {
		return params;
	}

	@Override
	public void setDevice(PrinterProtocol device) {
		this.device = device;
	}

	@Override
	public PrinterProtocol getDevice() {
		return device;
	}

	@Override
	public void addEvents(IPrinterEvents item) {
	}

	@Override
	public void deviceExecute(PrinterCommand command) throws Exception {
	}

	@Override
	public void connect() throws Exception {
	}

	@Override
	public void check(int errorCode) throws Exception {
	}

	@Override
	public void execute(PrinterCommand command) throws Exception {
	}

	@Override
	public int getSysPassword() {
		return 30;
	}

	@Override
	public int getUsrPassword() {
		return 1;
	}

	@Override
	public int getTaxPassword() {
		return 0;
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
		return 0;
	}

	@Override
	public void setTaxPassword(int taxPassword) {
	}

	@Override
	public void setUsrPassword(int usrPassword) {
	}

	@Override
	public void setSysPassword(int sysPassword) {
	}

	@Override
	public Beep beep() throws Exception {
		return new Beep();
	}

	@Override
	public int activateEJ() throws Exception {
		return 0;
	}

	@Override
	public int printEJActivationReport() throws Exception {
		return 0;
	}

	@Override
	public int initEJArchive() throws Exception {
		return 0;
	}

	@Override
	public int testEJArchive() throws Exception {
		return 0;
	}

	@Override
	public int closeEJArchive() throws Exception {
		return 0;
	}

	@Override
	public int cancelEJDocument() throws Exception {
		return 0;
	}

	@Override
	public int writeEJErrorCode(int errorCode) throws Exception {
		return 0;
	}

	@Override
	public ReadLongStatus readLongStatus() throws Exception {
		ReadLongStatus command = new ReadLongStatus();
		command.setStatus(longStatus);
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
		return shortStatus;
	}

	@Override
	public int printString(int station, String line) throws Exception {
		return 0;
	}

	@Override
	public int printBoldString(int station, String line) throws Exception {
		return 0;
	}

	@Override
	public void feedPaper(int station, int lineNumber) throws Exception {
	}

	@Override
	public int printStringFont(int station, FontNumber font, String line)
			throws Exception {
		return 0;
	}

	@Override
	public int printLine(int station, String line, FontNumber font)
			throws Exception {
		return 0;
	}

	@Override
	public String[] splitText(String text, int n, boolean wrap)
			throws Exception {
		return new String[0];
	}

	@Override
	public String[] splitText(String text, FontNumber font) throws Exception {
		return new String[0];
	}

	@Override
	public void printText(int station, String text, FontNumber font)
			throws Exception {
	}

	@Override
	public int updateFieldInfo(int tableNumber, int fieldNumber)
			throws Exception {
		return 0;
	}

	@Override
	public int writeTable(int tableNumber, int rowNumber, int fieldNumber,
			String fieldValue) throws Exception {
		return 0;
	}

	@Override
	public int readTable(int tableNumber, int rowNumber, int fieldNumber,
			String[] fieldValue) throws Exception {
		return 0;
	}

	@Override
	public int readTableInfo(int tableNumber, Object[] out) throws Exception {
		return 0;
	}

	@Override
	public ReadTableInfo readTableInfo(int tableNumber) throws Exception {
		return new ReadTableInfo();
	}

	@Override
	public PrintCashIn printCashIn(long sum) throws Exception {
		return new PrintCashIn();
	}

	@Override
	public PrintCashOut printCashOut(long sum) throws Exception {
		return new PrintCashOut();
	}

	@Override
	public ContinuePrint continuePrint() throws Exception {
		return new ContinuePrint();
	}

	@Override
	public BeginTest startTest(int runningPeriod) throws Exception {
		return new BeginTest();
	}

	@Override
	public EndTest stopTest() throws Exception {
		return new EndTest();
	}

	@Override
	public VoidFiscalReceipt cancelReceipt() throws Exception {
		return new VoidFiscalReceipt();
	}

	@Override
	public VoidFiscalReceipt cancelReceipt(int password) throws Exception {
		return new VoidFiscalReceipt();
	}

	@Override
	public EndFiscalReceipt closeReceipt(CloseRecParams params)
			throws Exception {
		return new EndFiscalReceipt();
	}

	@Override
	public long getSubtotal() throws Exception {
		return 0;
	}

	@Override
	public int readOperationRegister(OperationRegister register)
			throws Exception {
		return 0;
	}

	@Override
	public int readOperationRegister(int number) throws Exception {
		return 0;
	}

	@Override
	public int readCashRegister(CashRegister register) throws Exception {
		return 0;
	}

	@Override
	public long readCashRegister(int number) throws Exception {
		return 0;
	}

	@Override
	public PrintEJDayReportOnDates printEJDayReportOnDates(EJDate date1,
			EJDate date2, int reportType) throws Exception {
		return new PrintEJDayReportOnDates();
	}

	@Override
	public PrintFMReportDates printFMReportDates(PrinterDate date1,
			PrinterDate date2, int reportType) throws Exception {
		return new PrintFMReportDates();
	}

	@Override
	public PrintEJDayReportOnDays printEJReportDays(int day1, int day2,
			int reportType) throws Exception {
		return new PrintEJDayReportOnDays();
	}

	@Override
	public PrintFMReportDays printFMReportDays(int day1, int day2,
			int reportType) throws Exception {
		return new PrintFMReportDays();
	}

	@Override
	public void printSale(PriceItem item) throws Exception {
	}

	@Override
	public void printVoidSale(PriceItem item) throws Exception {
	}

	@Override
	public void printRefund(PriceItem item) throws Exception {
	}

	@Override
	public void printVoidRefund(PriceItem item) throws Exception {
	}

	@Override
	public PrintVoidItem printVoidItem(PriceItem item) throws Exception {
		return new PrintVoidItem();
	}

	@Override
	public PrintDiscount printDiscount(AmountItem item) throws Exception {
		return new PrintDiscount();
	}

	@Override
	public PrintVoidDiscount printVoidDiscount(AmountItem item)
			throws Exception {
		return new PrintVoidDiscount();
	}

	@Override
	public PrintCharge printCharge(AmountItem item) throws Exception {
		return new PrintCharge();
	}

	@Override
	public PrintVoidCharge printVoidCharge(AmountItem item) throws Exception {
		return new PrintVoidCharge();
	}

	@Override
	public ReadFMLastRecordDate readFMLastRecordDate() throws Exception {
		return new ReadFMLastRecordDate();
	}

	@Override
	public PrintXReport printXReport() throws Exception {
		return new PrintXReport();
	}

	@Override
	public PrintZReport printZReport() throws Exception {
		return new PrintZReport();
	}

	@Override
	public int printDepartmentReport() throws Exception {
		return 0;
	}

	@Override
	public int printTaxReport() throws Exception {
		return 0;
	}

	@Override
	public int printTotalizers() throws Exception {
		return 0;
	}

	@Override
	public int writeDate(PrinterDate date) throws Exception {
		return 0;
	}

	@Override
	public int confirmDate(PrinterDate date) throws Exception {
		return 0;
	}

	@Override
	public void writeTime(PrinterTime time) throws Exception {
	}

	@Override
	public void writePortParams(int portNumber, int baudRate, int timeout)
			throws Exception {
	}

	@Override
	public void printBarcode(String barcode) throws Exception {
	}

	@Override
	public void duplicateReceipt() throws Exception {
	}

	@Override
	public OpenReceipt openReceipt(int receiptType) throws Exception {
		return new OpenReceipt();
	}

	@Override
	public int loadGraphics(int lineNumber, byte[] data) throws Exception {
		return 0;
	}

	@Override
	public int loadGraphicsEx(int lineNumber, byte[] data) throws Exception {
		return 0;
	}

	@Override
	public int printGraphics(int line1, int line2) throws Exception {
		return 0;
	}

	@Override
	public void endDump() throws Exception {
	}

	@Override
	public int printGraphicsEx(int line1, int line2) throws Exception {
		return 0;
	}

	@Override
	public void printGraphicLine(int height, byte[] data) throws Exception {
	}

	@Override
	public int cutPaper(int cutType) throws Exception {
		return 0;
	}

	@Override
	public void openCashDrawer(int drawerNumber) throws Exception {
	}

	@Override
	public boolean checkEcrMode(int mode) throws Exception {
		return true;
	}

	@Override
	public PrinterStatus waitForPrinting() throws Exception {
		return printerStatus;
	}

	@Override
	public int[] getSupportedBaudRates() throws Exception {
		return getModel().getSupportedBaudRates();
	}

	@Override
	public boolean tryCancelReceipt(int password) throws Exception {
		return true;
	}

	@Override
	public void writeDecimalPoint(int position) throws Exception {
	}

	@Override
	public void resetFM() throws Exception {
	}

	@Override
	public void sysAdminCancelReceipt() throws Exception {
	}

	@Override
	public int getBaudRateIndex(int value) throws Exception {
		return 0;
	}

	@Override
	public void setBaudRate(int baudRate) throws Exception {
	}

	@Override
	public boolean connectDevice(int baudRate, int deviceBaudRate,
			int deviceByteTimeout) throws Exception {
		return true;
	}

	@Override
	public void checkBaudRate(int value) throws Exception {
	}

	@Override
	public void closePort() throws Exception {
	}

	@Override
	public void writeTables(PrinterTables tables) throws Exception {
	}

	@Override
	public void writeFields(PrinterFields fields) throws Exception {
	}

	@Override
	public void updateTableInfo(int tableNumber) throws Exception {
	}

	@Override
	public boolean isValidField(int tableNumber, int rowNumber, int fieldNumber)
			throws Exception {
		return true;
	}

	@Override
	public void readTables(PrinterTables tables) throws Exception {
	}

	@Override
	public PrinterStatus readShortPrinterStatus() throws Exception {
		return printerStatus;
	}

	@Override
	public PrinterStatus readLongPrinterStatus() throws Exception {
		return printerStatus;
	}

	@Override
	public PrinterStatus readPrinterStatus() throws Exception {
		return printerStatus;

	}

	@Override
	public int readDeviceMetrics() throws Exception {
		return 0;
	}

	@Override
	public DeviceMetrics getDeviceMetrics() {
		return deviceMetrics;
	}

	@Override
	public PrinterModel getModel() throws Exception {
		return model;
	}

	@Override
	public void updateModel() throws Exception {
	}

	@Override
	public boolean getWrapText() {
		return true;
	}

	@Override
	public void setWrapText(boolean value) {
	}

	@Override
	public void checkPaper(PrinterStatus status) throws Exception {
	}

	@Override
	public int bufferZReport() throws Exception {
		return 0;
	}

	@Override
	public int printBufferedZReport() throws Exception {
		return 0;
	}

	@Override
	public int printTrailer() throws Exception {
		return 0;
	}

	@Override
	public int printHeader() throws Exception {
		return 0;
	}

	@Override
	public int initTables() throws Exception {
		return 0;
	}

	@Override
	public void readTable(PrinterTable table) throws Exception {
	}

	@Override
	public void writeField(PrinterField field) throws Exception {
	}

	@Override
	public void readField(PrinterField field) throws Exception {
	}

	@Override
	public void loadImageData(int lineNumber, byte[] data) throws Exception {
	}

	@Override
	public void printImage(int line1, int line2) throws Exception {
	}

	@Override
	public int stopEJPrint() throws Exception {
		return 0;
	}

	@Override
	public int printEJDocument(int macNumber) throws Exception {
		return 0;
	}

	@Override
	public int printEJDayReport(int dayNumber) throws Exception {
		return 0;
	}

	@Override
	public int printEJDayTotal(int dayNumber) throws Exception {
		return 0;
	}

	@Override
	public int readEJDayReport(int dayNumber) throws Exception {
		return 0;
	}

	@Override
	public int readEJDayTotals(int dayNumber) throws Exception {
		return 0;
	}

	@Override
	public void writeParameter(int parameterID, int value) throws Exception {
	}

	@Override
	public void writeParameter(int parameterID, boolean value) throws Exception {
	}

	@Override
	public void writeParameter(int parameterID, String value) throws Exception {
	}

	@Override
	public String readParameter(int parameterID) throws Exception {
		return "";
	}

	@Override
	public int readIntParameter(int parameterID) throws Exception {
		return 0;
	}

	@Override
	public void printBarcode(PrinterBarcode barcode) throws Exception {
	}

	@Override
	public void sleep(long millis) {
	}

	@Override
	public PrinterImages getPrinterImages() {
		return new PrinterImages();
	}

	@Override
	public String processEscCommands(String text, CommandContext context)
			throws Exception {
		return "";
	}

	@Override
	public int getLineHeight(FontNumber font) throws Exception {
		return 0;
	}

	@Override
	public int getLineSpacing() throws Exception {
		return 0;
	}

	@Override
	public void checkImageSize(int firstLine, int imageWidth, int imageHeight)
			throws Exception {
	}

	@Override
	public int readLicense(String[] license) throws Exception {
		return 0;
	}

	@Override
	public void printSeparator(int separatorType, int height) throws Exception {
	}

	@Override
	public int getPrintWidth() throws Exception {
		return 0;
	}

	@Override
	public FlexCommands getCommands() throws Exception {
		return new FlexCommands();
	}

	@Override
	public int getCommandTimeout(int code) throws Exception {
		return 0;
	}

	@Override
	public int getResultCode() {
		return 0;
	}

	@Override
	public String getResultText() {
		return "";
	}

	@Override
	public ReadEJActivationReport readEJActivationReport() throws Exception {
		return new ReadEJActivationReport();
	}

	@Override
	public ReadEJStatus readEJStatus() throws Exception {
		return new ReadEJStatus();
	}

	@Override
	public String[] readEJActivationText(int maxCount) throws Exception {
		return new String[0];
	}

	@Override
	public ReadFMTotals readFMTotals(int mode) throws Exception {
		return new ReadFMTotals();
	}

	@Override
	public void setEscPrinter(NCR7167Printer escPrinter) {
	}

	@Override
	public void beginFiscalDay() throws Exception {
	}

	@Override
	public ReceiptImages getReceiptImages() {
		return null;
	}

	@Override
	public void printReceiptImage(int position) throws Exception {
	}

	@Override
	public PrinterImage getPrinterImage(int position) throws Exception {
		return null;

	}

	@Override
	public void printGraphics2(int line1, int line2) throws Exception {
	}

	@Override
	public void printImage(PrinterImage image) throws Exception {
	}

	@Override
	public void loadImage(PrinterImage image) throws Exception {
	}

}
