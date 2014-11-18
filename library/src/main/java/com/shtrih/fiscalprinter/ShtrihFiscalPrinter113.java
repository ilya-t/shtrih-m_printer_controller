package com.shtrih.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import com.shtrih.fiscalprinter.command.PrinterStatus;
import com.shtrih.jpos.fiscalprinter.SmFptrConst;

import jpos.BaseControl;
import jpos.FiscalPrinter;
import jpos.FiscalPrinterConst;
import jpos.FiscalPrinterControl113;
import jpos.JposConst;
import jpos.JposException;
import jpos.events.DirectIOListener;
import jpos.events.ErrorListener;
import jpos.events.OutputCompleteListener;
import jpos.events.StatusUpdateListener;

/**
 * Wrapper class to help using directIO codes *
 */
public class ShtrihFiscalPrinter113 implements BaseControl,
		FiscalPrinterControl113, JposConst {

	private final String encoding;
	private final FiscalPrinterControl113 printer;

	/**
	 * Creates a new instance of ShtrihFiscalPrinter
	 */
	public ShtrihFiscalPrinter113(FiscalPrinterControl113 printer,
			String encoding) {
		this.printer = printer;
		this.encoding = encoding;
	}

	public ShtrihFiscalPrinter113(FiscalPrinterControl113 printer) {
		this.printer = printer;
		this.encoding = "";
	}

	public ShtrihFiscalPrinter113(String encoding) {
		this.printer = new FiscalPrinter();
		this.encoding = encoding;
	}

	private String encodeString(String text) throws JposException {
		try {
			if (encoding.equals("")) {
				return text;
			} else {
				return new String(text.getBytes(encoding));
			}
		} catch (Exception e) {
			throw new JposException(JPOS_E_FAILURE, e.getMessage());
		}
	}

	private String decodeString(String text) throws JposException {
		try {
			if (encoding.equals("")) {
				return text;
			} else {
				return new String(text.getBytes(), encoding);
			}
		} catch (Exception e) {
			throw new JposException(JPOS_E_FAILURE, e.getMessage());
		}
	}

	@Override
	public boolean getCapCompareFirmwareVersion() throws JposException {
		return printer.getCapCompareFirmwareVersion();
	}

	@Override
	public boolean getCapUpdateFirmware() throws JposException {
		return printer.getCapUpdateFirmware();
	}

	@Override
	public void compareFirmwareVersion(String firmwareFileName, int[] result)
			throws JposException {
		printer.compareFirmwareVersion(encodeString(firmwareFileName), result);
	}

	@Override
	public void updateFirmware(String firmwareFileName) throws JposException {
		printer.updateFirmware(encodeString(firmwareFileName));
	}

	// FiscalPrinterControl18

	@Override
	public boolean getCapStatisticsReporting() throws JposException {
		return printer.getCapStatisticsReporting();
	}

	@Override
	public boolean getCapUpdateStatistics() throws JposException {
		return printer.getCapUpdateStatistics();
	}

	@Override
	public void resetStatistics(String statisticsBuffer) throws JposException {
		printer.resetStatistics(encodeString(statisticsBuffer));
	}

	@Override
	public void retrieveStatistics(String[] statisticsBuffer)
			throws JposException {
		printer.retrieveStatistics(statisticsBuffer);
	}

	@Override
	public void updateStatistics(String statisticsBuffer) throws JposException {
		printer.updateStatistics(encodeString(statisticsBuffer));
	}

	// FiscalPrinterControl17

	@Override
	public int getAmountDecimalPlaces() throws JposException {
		return printer.getAmountDecimalPlaces();
	}

	// FiscalPrinterControl16

	@Override
	public boolean getCapAdditionalHeader() throws JposException {
		return printer.getCapAdditionalHeader();
	}

	@Override
	public boolean getCapAdditionalTrailer() throws JposException {
		return printer.getCapAdditionalTrailer();
	}

	@Override
	public boolean getCapChangeDue() throws JposException {
		return printer.getCapChangeDue();
	}

	@Override
	public boolean getCapEmptyReceiptIsVoidable() throws JposException {
		return printer.getCapEmptyReceiptIsVoidable();
	}

	@Override
	public boolean getCapFiscalReceiptStation() throws JposException {
		return printer.getCapFiscalReceiptStation();
	}

	@Override
	public boolean getCapFiscalReceiptType() throws JposException {
		return printer.getCapFiscalReceiptType();
	}

	@Override
	public boolean getCapMultiContractor() throws JposException {
		return printer.getCapMultiContractor();
	}

	@Override
	public boolean getCapOnlyVoidLastItem() throws JposException {
		return printer.getCapOnlyVoidLastItem();
	}

	@Override
	public boolean getCapPackageAdjustment() throws JposException {
		return printer.getCapPackageAdjustment();
	}

	@Override
	public boolean getCapPostPreLine() throws JposException {
		return printer.getCapPostPreLine();
	}

	@Override
	public boolean getCapSetCurrency() throws JposException {
		return printer.getCapSetCurrency();
	}

	@Override
	public boolean getCapTotalizerType() throws JposException {
		return printer.getCapTotalizerType();
	}

	@Override
	public int getActualCurrency() throws JposException {
		return printer.getActualCurrency();
	}

	@Override
	public String getAdditionalHeader() throws JposException {
		return decodeString(printer.getAdditionalHeader());
	}

	@Override
	public void setAdditionalHeader(String additionalHeader)
			throws JposException {
		printer.setAdditionalHeader(encodeString(additionalHeader));
	}

	@Override
	public String getAdditionalTrailer() throws JposException {
		return decodeString(printer.getAdditionalTrailer());
	}

	@Override
	public void setAdditionalTrailer(String additionalTrailer)
			throws JposException {
		printer.setAdditionalTrailer(encodeString(additionalTrailer));
	}

	@Override
	public String getChangeDue() throws JposException {
		return decodeString(printer.getChangeDue());
	}

	@Override
	public void setChangeDue(String changeDue) throws JposException {
		printer.setChangeDue(encodeString(changeDue));
	}

	@Override
	public int getContractorId() throws JposException {
		return printer.getContractorId();
	}

	@Override
	public void setContractorId(int contractorId) throws JposException {
		printer.setContractorId(contractorId);
	}

	@Override
	public int getDateType() throws JposException {
		return printer.getDateType();
	}

	@Override
	public void setDateType(int dateType) throws JposException {
		printer.setDateType(dateType);
	}

	@Override
	public int getFiscalReceiptStation() throws JposException {
		return printer.getFiscalReceiptStation();
	}

	@Override
	public void setFiscalReceiptStation(int fiscalReceiptStation)
			throws JposException {
		printer.setFiscalReceiptStation(fiscalReceiptStation);
	}

	@Override
	public int getFiscalReceiptType() throws JposException {
		return printer.getFiscalReceiptType();
	}

	@Override
	public void setFiscalReceiptType(int fiscalReceiptType)
			throws JposException {
		printer.setFiscalReceiptType(fiscalReceiptType);
	}

	@Override
	public int getMessageType() throws JposException {
		return printer.getMessageType();
	}

	@Override
	public void setMessageType(int messageType) throws JposException {
		printer.setMessageType(messageType);
	}

	@Override
	public String getPostLine() throws JposException {
		return decodeString(printer.getPostLine());
	}

	@Override
	public void setPostLine(String postLine) throws JposException {
		printer.setPostLine(encodeString(postLine));
	}

	@Override
	public String getPreLine() throws JposException {
		return decodeString(printer.getPreLine());
	}

	@Override
	public void setPreLine(String preLine) throws JposException {
		printer.setPreLine(encodeString(preLine));
	}

	@Override
	public int getTotalizerType() throws JposException {
		return printer.getTotalizerType();
	}

	@Override
	public void setTotalizerType(int totalizerType) throws JposException {
		printer.setTotalizerType(totalizerType);
	}

	@Override
	public void setCurrency(int newCurrency) throws JposException {
		printer.setCurrency(newCurrency);
	}

	@Override
	public void printRecCash(long amount) throws JposException {
		printer.printRecCash(amount);
	}

	@Override
	public void printRecItemFuel(String description, long price, int quantity,
			int vatInfo, long unitPrice, String unitName, long specialTax,
			String specialTaxName) throws JposException {
		printer.printRecItemFuel(encodeString(description), price, quantity,
				vatInfo, unitPrice, encodeString(unitName), specialTax,
				encodeString(specialTaxName));
	}

	@Override
	public void printRecItemFuelVoid(String description, long price,
			int vatInfo, long specialTax) throws JposException {
		printer.printRecItemFuelVoid(encodeString(description), price, vatInfo,
				specialTax);
	}

	@Override
	public void printRecPackageAdjustment(int adjustmentType,
			String description, String vatAdjustment) throws JposException {
		printer.printRecPackageAdjustment(adjustmentType,
				encodeString(description), encodeString(vatAdjustment));
	}

	@Override
	public void printRecPackageAdjustVoid(int adjustmentType,
			String vatAdjustment) throws JposException {
		printer.printRecPackageAdjustVoid(adjustmentType,
				encodeString(vatAdjustment));
	}

	@Override
	public void printRecRefundVoid(String description, long amount, int vatInfo)
			throws JposException {
		printer.printRecRefundVoid(encodeString(description), amount, vatInfo);
	}

	@Override
	public void printRecSubtotalAdjustVoid(int adjustmentType, long amount)
			throws JposException {
		printer.printRecSubtotalAdjustVoid(adjustmentType, amount);
	}

	@Override
	public void printRecTaxID(String taxID) throws JposException {
		printer.printRecTaxID(encodeString(taxID));
	}

	// FiscalPrinterControl13

	@Override
	public boolean getCapAdditionalLines() throws JposException {
		return printer.getCapAdditionalLines();
	}

	@Override
	public boolean getCapAmountAdjustment() throws JposException {
		return printer.getCapAmountAdjustment();
	}

	@Override
	public boolean getCapAmountNotPaid() throws JposException {
		return printer.getCapAmountNotPaid();
	}

	@Override
	public boolean getCapCheckTotal() throws JposException {
		return printer.getCapCheckTotal();
	}

	@Override
	public boolean getCapCoverSensor() throws JposException {
		return printer.getCapCoverSensor();
	}

	@Override
	public boolean getCapDoubleWidth() throws JposException {
		return printer.getCapDoubleWidth();
	}

	@Override
	public boolean getCapDuplicateReceipt() throws JposException {
		return printer.getCapDuplicateReceipt();
	}

	@Override
	public boolean getCapFixedOutput() throws JposException {
		return printer.getCapFixedOutput();
	}

	@Override
	public boolean getCapHasVatTable() throws JposException {
		return printer.getCapHasVatTable();
	}

	@Override
	public boolean getCapIndependentHeader() throws JposException {
		return printer.getCapIndependentHeader();
	}

	@Override
	public boolean getCapItemList() throws JposException {
		return printer.getCapItemList();
	}

	@Override
	public boolean getCapJrnEmptySensor() throws JposException {
		return printer.getCapJrnEmptySensor();
	}

	@Override
	public boolean getCapJrnNearEndSensor() throws JposException {
		return printer.getCapJrnNearEndSensor();
	}

	@Override
	public boolean getCapJrnPresent() throws JposException {
		return printer.getCapJrnPresent();
	}

	@Override
	public boolean getCapNonFiscalMode() throws JposException {
		return printer.getCapNonFiscalMode();
	}

	@Override
	public boolean getCapOrderAdjustmentFirst() throws JposException {
		return printer.getCapOrderAdjustmentFirst();
	}

	@Override
	public boolean getCapPercentAdjustment() throws JposException {
		return printer.getCapPercentAdjustment();
	}

	@Override
	public boolean getCapPositiveAdjustment() throws JposException {
		return printer.getCapPositiveAdjustment();
	}

	@Override
	public boolean getCapPowerLossReport() throws JposException {
		return printer.getCapPowerLossReport();
	}

	@Override
	public int getCapPowerReporting() throws JposException {
		return printer.getCapPowerReporting();
	}

	@Override
	public boolean getCapPredefinedPaymentLines() throws JposException {
		return printer.getCapPredefinedPaymentLines();
	}

	@Override
	public boolean getCapReceiptNotPaid() throws JposException {
		return printer.getCapReceiptNotPaid();
	}

	@Override
	public boolean getCapRecEmptySensor() throws JposException {
		return printer.getCapRecEmptySensor();
	}

	@Override
	public boolean getCapRecNearEndSensor() throws JposException {
		return printer.getCapRecNearEndSensor();
	}

	@Override
	public boolean getCapRecPresent() throws JposException {
		return printer.getCapRecPresent();
	}

	@Override
	public boolean getCapRemainingFiscalMemory() throws JposException {
		return printer.getCapRemainingFiscalMemory();
	}

	@Override
	public boolean getCapReservedWord() throws JposException {
		return printer.getCapReservedWord();
	}

	@Override
	public boolean getCapSetHeader() throws JposException {
		return printer.getCapSetHeader();
	}

	@Override
	public boolean getCapSetPOSID() throws JposException {
		return printer.getCapSetPOSID();
	}

	@Override
	public boolean getCapSetStoreFiscalID() throws JposException {
		return printer.getCapSetStoreFiscalID();
	}

	@Override
	public boolean getCapSetTrailer() throws JposException {
		return printer.getCapSetTrailer();
	}

	@Override
	public boolean getCapSetVatTable() throws JposException {
		return printer.getCapSetVatTable();
	}

	@Override
	public boolean getCapSlpEmptySensor() throws JposException {
		return printer.getCapSlpEmptySensor();
	}

	@Override
	public boolean getCapSlpFiscalDocument() throws JposException {
		return printer.getCapSlpFiscalDocument();
	}

	@Override
	public boolean getCapSlpFullSlip() throws JposException {
		return printer.getCapSlpFullSlip();
	}

	@Override
	public boolean getCapSlpNearEndSensor() throws JposException {
		return printer.getCapSlpNearEndSensor();
	}

	@Override
	public boolean getCapSlpPresent() throws JposException {
		return printer.getCapSlpPresent();
	}

	@Override
	public boolean getCapSlpValidation() throws JposException {
		return printer.getCapSlpValidation();
	}

	@Override
	public boolean getCapSubAmountAdjustment() throws JposException {
		return printer.getCapSubAmountAdjustment();
	}

	@Override
	public boolean getCapSubPercentAdjustment() throws JposException {
		return printer.getCapSubPercentAdjustment();
	}

	@Override
	public boolean getCapSubtotal() throws JposException {
		return printer.getCapSubtotal();
	}

	@Override
	public boolean getCapTrainingMode() throws JposException {
		return printer.getCapTrainingMode();
	}

	@Override
	public boolean getCapValidateJournal() throws JposException {
		return printer.getCapValidateJournal();
	}

	@Override
	public boolean getCapXReport() throws JposException {
		return printer.getCapXReport();
	}

	// Properties

	@Override
	public int getAmountDecimalPlace() throws JposException {
		return printer.getAmountDecimalPlace();
	}

	@Override
	public boolean getAsyncMode() throws JposException {
		return printer.getAsyncMode();
	}

	@Override
	public void setAsyncMode(boolean asyncMode) throws JposException {
		printer.setAsyncMode(asyncMode);
	}

	@Override
	public boolean getCheckTotal() throws JposException {
		return printer.getCheckTotal();
	}

	@Override
	public void setCheckTotal(boolean checkTotal) throws JposException {
		printer.setCheckTotal(checkTotal);
	}

	@Override
	public int getCountryCode() throws JposException {
		return printer.getCountryCode();
	}

	@Override
	public boolean getCoverOpen() throws JposException {
		return printer.getCoverOpen();
	}

	@Override
	public boolean getDayOpened() throws JposException {
		return printer.getDayOpened();
	}

	@Override
	public int getDescriptionLength() throws JposException {
		return printer.getDescriptionLength();
	}

	@Override
	public boolean getDuplicateReceipt() throws JposException {
		return printer.getDuplicateReceipt();
	}

	@Override
	public void setDuplicateReceipt(boolean duplicateReceipt)
			throws JposException {
		printer.setDuplicateReceipt(duplicateReceipt);
	}

	@Override
	public int getErrorLevel() throws JposException {
		return printer.getErrorLevel();
	}

	@Override
	public int getErrorOutID() throws JposException {
		return printer.getErrorOutID();
	}

	@Override
	public int getErrorState() throws JposException {
		return printer.getErrorState();
	}

	@Override
	public int getErrorStation() throws JposException {
		return printer.getErrorStation();
	}

	@Override
	public String getErrorString() throws JposException {
		return decodeString(printer.getErrorString());
	}

	@Override
	public boolean getFlagWhenIdle() throws JposException {
		return printer.getFlagWhenIdle();
	}

	@Override
	public void setFlagWhenIdle(boolean flagWhenIdle) throws JposException {
		printer.setFlagWhenIdle(flagWhenIdle);
	}

	@Override
	public boolean getJrnEmpty() throws JposException {
		return printer.getJrnEmpty();
	}

	@Override
	public boolean getJrnNearEnd() throws JposException {
		return printer.getJrnNearEnd();
	}

	@Override
	public int getMessageLength() throws JposException {
		return printer.getMessageLength();
	}

	@Override
	public int getNumHeaderLines() throws JposException {
		return printer.getNumHeaderLines();
	}

	@Override
	public int getNumTrailerLines() throws JposException {
		return printer.getNumTrailerLines();
	}

	@Override
	public int getNumVatRates() throws JposException {
		return printer.getNumVatRates();
	}

	@Override
	public int getOutputID() throws JposException {
		return printer.getOutputID();
	}

	@Override
	public int getPowerNotify() throws JposException {
		return printer.getPowerNotify();
	}

	@Override
	public void setPowerNotify(int powerNotify) throws JposException {
		printer.setPowerNotify(powerNotify);
	}

	@Override
	public int getPowerState() throws JposException {
		return printer.getPowerState();
	}

	@Override
	public String getPredefinedPaymentLines() throws JposException {
		return decodeString(printer.getPredefinedPaymentLines());
	}

	@Override
	public int getPrinterState() throws JposException {
		return printer.getPrinterState();
	}

	@Override
	public int getQuantityDecimalPlaces() throws JposException {
		return printer.getQuantityDecimalPlaces();
	}

	@Override
	public int getQuantityLength() throws JposException {
		return printer.getQuantityLength();
	}

	@Override
	public boolean getRecEmpty() throws JposException {
		return printer.getRecEmpty();
	}

	@Override
	public boolean getRecNearEnd() throws JposException {
		return printer.getRecNearEnd();
	}

	@Override
	public int getRemainingFiscalMemory() throws JposException {
		return printer.getRemainingFiscalMemory();
	}

	@Override
	public String getReservedWord() throws JposException {
		return decodeString(printer.getReservedWord());
	}

	@Override
	public boolean getSlpEmpty() throws JposException {
		return printer.getSlpEmpty();
	}

	@Override
	public boolean getSlpNearEnd() throws JposException {
		return printer.getSlpNearEnd();
	}

	@Override
	public int getSlipSelection() throws JposException {
		return printer.getSlipSelection();
	}

	@Override
	public void setSlipSelection(int slipSelection) throws JposException {
		printer.setSlipSelection(slipSelection);
	}

	@Override
	public boolean getTrainingModeActive() throws JposException {
		return printer.getTrainingModeActive();
	}

	@Override
	public void beginFiscalDocument(int documentAmount) throws JposException {
		printer.beginFiscalDocument(documentAmount);
	}

	@Override
	public void beginFiscalReceipt(boolean printHeader) throws JposException {
		printer.beginFiscalReceipt(printHeader);
	}

	@Override
	public void beginFixedOutput(int station, int documentType)
			throws JposException {
		printer.beginFixedOutput(station, documentType);
	}

	@Override
	public void beginInsertion(int timeout) throws JposException {
		printer.beginInsertion(timeout);
	}

	@Override
	public void beginItemList(int vatID) throws JposException {
		printer.beginItemList(vatID);
	}

	@Override
	public void beginNonFiscal() throws JposException {
		printer.beginNonFiscal();
	}

	@Override
	public void beginRemoval(int timeout) throws JposException {
		printer.beginRemoval(timeout);
	}

	@Override
	public void beginTraining() throws JposException {
		printer.beginTraining();
	}

	@Override
	public void clearError() throws JposException {
		printer.clearError();
	}

	@Override
	public void clearOutput() throws JposException {
		printer.clearOutput();
	}

	@Override
	public void endFiscalDocument() throws JposException {
		printer.endFiscalDocument();
	}

	@Override
	public void endFiscalReceipt(boolean printHeader) throws JposException {
		printer.endFiscalReceipt(printHeader);
	}

	@Override
	public void endFixedOutput() throws JposException {
		printer.endFixedOutput();
	}

	@Override
	public void endInsertion() throws JposException {
		printer.endInsertion();
	}

	@Override
	public void endItemList() throws JposException {
		printer.endItemList();
	}

	@Override
	public void endNonFiscal() throws JposException {
		printer.endNonFiscal();
	}

	@Override
	public void endRemoval() throws JposException {
		printer.endRemoval();
	}

	@Override
	public void endTraining() throws JposException {
		printer.endTraining();
	}

	@Override
	public void getData(int dataItem, int[] optArgs, String[] data)
			throws JposException {
		printer.getData(dataItem, optArgs, data);
	}

	@Override
	public void getDate(String[] Date) throws JposException {
		printer.getDate(Date);
	}

	@Override
	public void getTotalizer(int vatID, int optArgs, String[] data)
			throws JposException {
		printer.getTotalizer(vatID, optArgs, data);
	}

	@Override
	public void getVatEntry(int vatID, int optArgs, int[] vatRate)
			throws JposException {
		printer.getVatEntry(vatID, optArgs, vatRate);
	}

	@Override
	public void printDuplicateReceipt() throws JposException {
		printer.printDuplicateReceipt();
	}

	@Override
	public void printFiscalDocumentLine(String documentLine)
			throws JposException {
		printer.printFiscalDocumentLine(encodeString(documentLine));
	}

	@Override
	public void printFixedOutput(int documentType, int lineNumber, String data)
			throws JposException {
		printer.printFixedOutput(documentType, lineNumber, encodeString(data));
	}

	@Override
	public void printNormal(int station, String data) throws JposException {
		printer.printNormal(station, encodeString(data));
	}

	@Override
	public void printPeriodicTotalsReport(String date1, String date2)
			throws JposException {
		printer.printPeriodicTotalsReport(encodeString(date1),
				encodeString(date2));
	}

	@Override
	public void printPowerLossReport() throws JposException {
		printer.printPowerLossReport();
	}

	@Override
	public void printRecItem(String description, long price, int quantity,
			int vatInfo, long unitPrice, String unitName) throws JposException {
		printer.printRecItem(encodeString(description), price, quantity,
				vatInfo, unitPrice, encodeString(unitName));
	}

	@Override
	public void printRecItemAdjustment(int adjustmentType, String description,
			long amount, int vatInfo) throws JposException {
		printer.printRecItemAdjustment(adjustmentType,
				encodeString(description), amount, vatInfo);
	}

	@Override
	public void printRecMessage(String message) throws JposException {
		printer.printRecMessage(encodeString(message));
	}

	@Override
	public void printRecNotPaid(String description, long amount)
			throws JposException {
		printer.printRecNotPaid(encodeString(description), amount);
	}

	@Override
	public void printRecRefund(String description, long amount, int vatInfo)
			throws JposException {
		printer.printRecRefund(encodeString(description), amount, vatInfo);
	}

	@Override
	public void printRecSubtotal(long amount) throws JposException {
		printer.printRecSubtotal(amount);
	}

	@Override
	public void printRecSubtotalAdjustment(int adjustmentType,
			String description, long amount) throws JposException {
		printer.printRecSubtotalAdjustment(adjustmentType,
				encodeString(description), amount);
	}

	@Override
	public void printRecTotal(long total, long payment, String description)
			throws JposException {
		printer.printRecTotal(total, payment, encodeString(description));
	}

	@Override
	public void printRecVoid(String description) throws JposException {
		printer.printRecVoid(encodeString(description));
	}

	@Override
	public void printRecVoidItem(String description, long amount, int quantity,
			int adjustmentType, long adjustment, int vatInfo)
			throws JposException {
		printer.printRecVoidItem(encodeString(description), amount, quantity,
				adjustmentType, adjustment, vatInfo);
	}

	@Override
	public void printReport(int reportType, String startNum, String endNum)
			throws JposException {
		printer.printReport(reportType, encodeString(startNum), endNum);
	}

	@Override
	public void printXReport() throws JposException {
		printer.printXReport();
	}

	@Override
	public void printZReport() throws JposException {
		printer.printZReport();
	}

	@Override
	public void resetPrinter() throws JposException {
		printer.resetPrinter();
	}

	@Override
	public void setDate(String date) throws JposException {
		printer.setDate(encodeString(date));
	}

	public String getHeaderLine(int lineNumber) throws JposException {
		int[] data = new int[1];
		String[] lines = new String[1];
		data[0] = lineNumber;
		directIO(SmFptrConst.SMFPTR_DIO_GET_HEADER_LINE, data, lines);
		return lines[0];
	}

	public String getTrailerLine(int lineNumber) throws JposException {
		int[] data = new int[1];
		String[] lines = new String[1];
		data[0] = lineNumber;
		directIO(SmFptrConst.SMFPTR_DIO_GET_TRAILER_LINE, data, lines);
		return lines[0];
	}

	@Override
	public void setHeaderLine(int lineNumber, String text, boolean doubleWidth)
			throws JposException {
		printer.setHeaderLine(lineNumber, encodeString(text), doubleWidth);
	}

	@Override
	public void setPOSID(String POSID, String cashierID) throws JposException {
		printer.setPOSID(encodeString(POSID), encodeString(cashierID));
	}

	@Override
	public void setStoreFiscalID(String ID) throws JposException {
		printer.setStoreFiscalID(encodeString(ID));
	}

	@Override
	public void setTrailerLine(int lineNumber, String text, boolean doubleWidth)
			throws JposException {
		printer.setTrailerLine(lineNumber, encodeString(text), doubleWidth);
	}

	@Override
	public void setVatTable() throws JposException {
		printer.setVatTable();
	}

	@Override
	public void setVatValue(int vatID, String vatValue) throws JposException {
		printer.setVatValue(vatID, encodeString(vatValue));
	}

	@Override
	public void verifyItem(String itemName, int vatID) throws JposException {
		printer.verifyItem(encodeString(itemName), vatID);
	}

	@Override
	public void addDirectIOListener(DirectIOListener l) {
		printer.addDirectIOListener(l);
	}

	@Override
	public void removeDirectIOListener(DirectIOListener l) {
		printer.removeDirectIOListener(l);
	}

	@Override
	public void addErrorListener(ErrorListener l) {
		printer.addErrorListener(l);
	}

	@Override
	public void removeErrorListener(ErrorListener l) {
		printer.removeErrorListener(l);
	}

	@Override
	public void addOutputCompleteListener(OutputCompleteListener l) {
		printer.addOutputCompleteListener(l);
	}

	@Override
	public void removeOutputCompleteListener(OutputCompleteListener l) {
		printer.removeOutputCompleteListener(l);
	}

	@Override
	public void addStatusUpdateListener(StatusUpdateListener l) {
		printer.addStatusUpdateListener(l);
	}

	@Override
	public void removeStatusUpdateListener(StatusUpdateListener l) {
		printer.removeStatusUpdateListener(l);
	}

	// BaseControl

	@Override
	public String getCheckHealthText() throws JposException {
		return decodeString(printer.getCheckHealthText());
	}

	@Override
	public boolean getClaimed() throws JposException {
		return printer.getClaimed();
	}

	@Override
	public String getDeviceControlDescription() {
		return printer.getDeviceControlDescription();
	}

	@Override
	public int getDeviceControlVersion() {
		return printer.getDeviceControlVersion();
	}

	@Override
	public boolean getDeviceEnabled() throws JposException {
		return printer.getDeviceEnabled();
	}

	@Override
	public void setDeviceEnabled(boolean deviceEnabled) throws JposException {
		printer.setDeviceEnabled(deviceEnabled);
	}

	@Override
	public String getDeviceServiceDescription() throws JposException {
		return decodeString(printer.getDeviceServiceDescription());
	}

	@Override
	public int getDeviceServiceVersion() throws JposException {
		return printer.getDeviceServiceVersion();
	}

	@Override
	public boolean getFreezeEvents() throws JposException {
		return printer.getFreezeEvents();
	}

	@Override
	public void setFreezeEvents(boolean freezeEvents) throws JposException {
		printer.setFreezeEvents(freezeEvents);
	}

	@Override
	public String getPhysicalDeviceDescription() throws JposException {
		return decodeString(printer.getPhysicalDeviceDescription());
	}

	@Override
	public String getPhysicalDeviceName() throws JposException {
		return decodeString(printer.getPhysicalDeviceName());
	}

	@Override
	public int getState() {
		return printer.getState();
	}

	@Override
	public boolean getCapPositiveSubtotalAdjustment() throws JposException {
		return printer.getCapPositiveSubtotalAdjustment();
	}

	// Methods

	@Override
	public void claim(int timeout) throws JposException {
		printer.claim(timeout);
	}

	@Override
	public void close() throws JposException {
		printer.close();
	}

	@Override
	public void checkHealth(int level) throws JposException {
		printer.checkHealth(level);
	}

	@Override
	public void directIO(int command, int[] data, Object object)
			throws JposException {
		printer.directIO(command, data, object);
	}

	@Override
	public void open(String logicalDeviceName) throws JposException {
		printer.open(encodeString(logicalDeviceName));
	}

	@Override
	public void release() throws JposException {
		printer.release();
	}

	public void printText(String text) throws JposException {
		int data[] = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_PRINT_TEXT, data, text);
	}

	public void printText(String text, FontNumber font) throws JposException {
		int data[] = new int[1];
		data[0] = font.getValue();
		directIO(SmFptrConst.SMFPTR_DIO_PRINT_TEXT, data, text);
	}

	public void setParameter(int paramType, int paramValue)
			throws JposException {
		int data[] = new int[1];
		int object[] = new int[1];
		data[0] = paramType;
		object[0] = paramValue;
		directIO(SmFptrConst.SMFPTR_DIO_SET_DRIVER_PARAMETER, data, object);
	}

	public int getParameter(int paramType) throws JposException {
		int data[] = new int[1];
		int object[] = new int[1];
		data[0] = paramType;
		directIO(SmFptrConst.SMFPTR_DIO_GET_DRIVER_PARAMETER, data, object);
		return object[0];
	}

	/**
	 * Set number of header lines *
	 */
	public void setNumHeaderLines(int value) throws JposException {
		setParameter(SmFptrConst.SMFPTR_DIO_PARAM_NUMHEADERLINES, value);
	}

	/**
	 * Set number of trailer lines *
	 */
	public void setNumTrailerLines(int value) throws JposException {
		setParameter(SmFptrConst.SMFPTR_DIO_PARAM_NUMTRAILERLINES, value);
	}

	/**
	 * Select device (FM or EJ) for printReport method *
	 */
	public void setReportDevice(int value) throws JposException {
		setParameter(SmFptrConst.SMFPTR_DIO_PARAM_REPORT_DEVICE, value);
	}

	/**
	 * Get device type (FM or EJ) for printReport method *
	 */
	public int getReportDevice() throws JposException {
		return getParameter(SmFptrConst.SMFPTR_DIO_PARAM_REPORT_DEVICE);
	}

	/**
	 * Set report type (full or short) for printReport method *
	 */
	public void setReportType(int value) throws JposException {
		setParameter(SmFptrConst.SMFPTR_DIO_PARAM_REPORT_TYPE, value);
	}

	/**
	 * Set report type (full or short) for printReport method *
	 */
	public int getReportType() throws JposException {
		return getParameter(SmFptrConst.SMFPTR_DIO_PARAM_REPORT_TYPE);
	}

	/**
	 * Write tables from file "fileName". File is in UTF-8 *
	 */
	public void writeTables(String fileName) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_WRITE_TABLES, data, fileName);
	}

	/**
	 * Read tables to file "fileName". File is in UTF-8 *
	 */
	public void readTables(String fileName) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_TABLES, data, fileName);
	}

	/**
	 * Get department number *
	 */
	public int getDepartment() throws JposException {
		int[] data = new int[1];
		int[] value = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_GET_DEPARTMENT, data, value);
		return value[0];
	}

	/**
	 * Set department number *
	 */
	public void setDepartment(int department) throws JposException {
		int[] data = new int[1];
		int[] value = new int[1];
		value[0] = department;
		directIO(SmFptrConst.SMFPTR_DIO_SET_DEPARTMENT, data, value);
	}

	/**
	 * Read table value *
	 */
	public String readTable(int tableNumber, int rowNumber, int fieldNumber)
			throws JposException {
		int[] data = new int[3];
		data[0] = tableNumber;
		data[1] = rowNumber;
		data[2] = fieldNumber;
		String[] fieldValue = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READTABLE, data, fieldValue);
		return fieldValue[0];
	}

	/**
	 * Write table value *
	 */
	public void writeTable(int tableNumber, int rowNumber, int fieldNumber,
			String fieldValue) throws JposException {
		int[] data = new int[3];
		data[0] = tableNumber;
		data[1] = rowNumber;
		data[2] = fieldNumber;
		String[] value = new String[1];
		value[0] = fieldValue;
		directIO(SmFptrConst.SMFPTR_DIO_WRITETABLE, data, value);
	}

	/**
	 * Read payment name, number = 1..4 *
	 */
	public String readPaymentName(int number) throws JposException {
		int[] data = new int[1];
		data[0] = number;
		String[] fieldValue = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_PAYMENT_NAME, data, fieldValue);
		return fieldValue[0];
	}

	/**
	 * Write payment name, number = 1..4 *
	 */
	public void writePaymentName(int number, String value) throws JposException {
		int[] data = new int[1];
		data[0] = number;
		String[] fieldValue = new String[1];
		fieldValue[0] = value;
		directIO(SmFptrConst.SMFPTR_DIO_WRITE_PAYMENT_NAME, data, fieldValue);
	}

	/**
	 * Read day end required value *
	 */
	public boolean readDayEnd() throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_DAY_END, data, null);
		return data[0] != 0;
	}

	/**
	 * Print barcode *
	 */
	public void printBarcode(String barcode, String label, int barcodeType,
			int barcodeHeight, int printType, int barWidth, int textPosition,
			int textFont, int aspectRatio) throws JposException {
		int data[] = new int[7];
		String[] command = new String[2];

		command[0] = barcode; // barcode data
		command[1] = label; // barcode label
		data[0] = barcodeType; // barcode type
		data[1] = barcodeHeight; // barcode height in pixels
		data[2] = printType; // print type
		data[3] = barWidth; // barcode bar width in pixels
		data[4] = textPosition; // text position
		data[5] = textFont; // text font
		data[6] = aspectRatio; // narrow to width ratio, 3 by default
		directIO(SmFptrConst.SMFPTR_DIO_PRINT_BARCODE, data, command);
	}

	/**
	 * Load image *
	 */
	public int loadImage(String fileName) throws JposException {
		int[] data = new int[1];
		String[] command = new String[1];
		command[0] = fileName;
		directIO(SmFptrConst.SMFPTR_DIO_LOAD_IMAGE, data, command);
		return data[0];
	}

	public void printImage(int imageIndex) throws JposException {
		int[] data = new int[1];
		data[0] = imageIndex;
		directIO(SmFptrConst.SMFPTR_DIO_PRINT_IMAGE, data, null);
	}

	public void clearImages() throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_CLEAR_IMAGES, data, null);
	}

	public void addLogo(int imageIndex, int logoPosition) throws JposException {
		int[] data = new int[2];
		data[0] = imageIndex;
		data[1] = logoPosition;
		directIO(SmFptrConst.SMFPTR_DIO_ADD_LOGO, data, null);
	}

	public void clearLogo() throws JposException {
		int[] data = new int[0];
		directIO(SmFptrConst.SMFPTR_DIO_CLEAR_LOGO, data, null);
	}

	public void printLine(int lineType, int lineHeight) throws JposException {
		int[] data = new int[2];
		data[0] = lineHeight;
		data[1] = lineType;
		directIO(SmFptrConst.SMFPTR_DIO_PRINT_LINE, data, null);
	}

	public String readSerial() throws JposException {
		String[] serial = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_SERIAL, null, serial);
		return serial[0];
	}

	public String readLicense() throws JposException {
		String[] data = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_LICENSE, null, data);
		return data[0];
	}

	public String readEJSerial() throws JposException {
		String[] serial = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_EJ_SERIAL, null, serial);
		return serial[0];
	}

	public void openCashDrawer(int drawerNumber) throws JposException {
		int[] data = new int[1];
		data[0] = drawerNumber;
		directIO(SmFptrConst.SMFPTR_DIO_OPEN_DRAWER, data, null);
	}

	public boolean readDrawerState() throws JposException {
		int[] data = new int[1];
		data[0] = 0;
		directIO(SmFptrConst.SMFPTR_DIO_READ_DRAWER_STATE, data, null);
		return data[0] != 0;
	}

	public PrinterStatus readPrinterStatus() throws JposException {
		int[] data = new int[4];
		directIO(SmFptrConst.SMFPTR_DIO_READ_PRINTER_STATUS, data, null);
                
                PrinterStatus printerStatus = new PrinterStatus();
                printerStatus.setMode(data[0]);
                printerStatus.setSubmode(data[1]);
                printerStatus.setFlags(data[2]);
                printerStatus.setOperator(data[3]);
		return printerStatus;
	}

	public class CashRegister {

		private final long value;
		private final String text;

		public CashRegister(long value, String text) {
			this.value = value;
			this.text = text;
		}

		public long getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}

	public class OperRegister {

		private final long value;
		private final String text;

		public OperRegister(long value, String text) {
			this.value = value;
			this.text = text;
		}

		public long getValue() {
			return value;
		}

		public String getText() {
			return text;
		}
	}

	public CashRegister readCashRegister(int number) throws JposException {
		int[] data = new int[1];
		String[] lines = new String[2];
		data[0] = number;
		directIO(SmFptrConst.SMFPTR_DIO_READ_CASH_REG, data, lines);
		return new CashRegister(Long.parseLong(lines[0]), lines[1]);
	}

	public OperRegister readOperRegister(int number) throws JposException {
		int[] data = new int[1];
		String[] lines = new String[2];
		data[0] = number;
		directIO(SmFptrConst.SMFPTR_DIO_READ_OPER_REG, data, lines);
		return new OperRegister(Long.parseLong(lines[0]), lines[1]);
	}

	public long getReceiptNumber() throws JposException {
		String[] data = new String[1];
		getData(FiscalPrinterConst.FPTR_GD_RECEIPT_NUMBER, null, data);
		return Long.parseLong(data[0]);
	}

	public void saveXmlZReport(String fileName) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_XML_ZREPORT, data, fileName);
	}

	public void saveCsvZReport(String fileName) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_CSV_ZREPORT, data, fileName);
	}

	public byte[] executeCommand(byte[] tx, int timeout) throws JposException {
		int[] data = new int[1];
		Object[] object = new Object[2];
		data[0] = timeout;
		object[0] = tx;
		directIO(SmFptrConst.SMFPTR_DIO_COMMAND, data, object);
		byte[] rx = (byte[]) object[1];
		return rx;
	}

	public String executeCommand(int code, int timeout, String inParams)
			throws JposException {
		int[] data = new int[2];
		data[0] = code;
		data[1] = timeout;
		String[] lines = new String[2];
		lines[0] = inParams;
		directIO(SmFptrConst.SMFPTR_DIO_STRCOMMAND, data, lines);
		String outParams = lines[1];
		return outParams;
	}

	public void waitForPrinting() throws JposException {
		directIO(SmFptrConst.SMFPTR_DIO_WAIT_PRINT, null, null);
	}

	public void readStatus1() throws JposException {
		byte[] tx = { 0x11, 0x1E, 0x00, 0x00, 0x00 };
		byte[] rx = executeCommand(tx, 10000);
	}

	public void readStatus2() throws JposException {
		String inParams = "30";
		String outParams = "";
		outParams = executeCommand(0x11, 10000, inParams);
	}

	public int readDayStatus() throws JposException {

		int[] data = new int[1];
		Object[] object = new Object[0];
		directIO(SmFptrConst.SMFPTR_DIO_READ_DAY_STATUS, data, object);
		return data[0];
	}

	public boolean isReadyFiscal(String[] text) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_IS_READY_FISCAL, data, text);
		return data[0] != 0;
	}

	public boolean isReadyNonfiscal(String[] text) throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_IS_READY_NONFISCAL, data, text);
		return data[0] != 0;
	}

	public int readMaxGraphics() throws JposException {
		int[] data = new int[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_MAX_GRAPHICS, data, null);
		return data[0];
	}

	public String getDailyTotal() throws JposException {
		String[] data = new String[1];
		data[0] = "";
		getData(FiscalPrinterConst.FPTR_GD_DAILY_TOTAL, null, data);
		return data[0];

	}

	public String getDailyTotal(int mode) throws JposException {
		int[] optArgs = new int[1];
		optArgs[0] = mode;
		String[] data = new String[1];
		data[0] = "";
		getData(FiscalPrinterConst.FPTR_GD_DAILY_TOTAL, optArgs, data);
		return data[0];
	}

	public String getGrandTotal(int mode) throws JposException {
		int[] optArgs = new int[1];
		optArgs[0] = mode;
		String[] data = new String[1];
		data[0] = "";
		getData(FiscalPrinterConst.FPTR_GD_GRAND_TOTAL, optArgs, data);
		return data[0];
	}

	public int getTextLength(int fontNumber) throws JposException {
		int[] data = new int[2];
		data[0] = fontNumber;
		directIO(SmFptrConst.SMFPTR_DIO_GET_TEXT_LENGTH, data, null);
		return data[1];
	}

	public String readCashierName() throws JposException {
		String[] lines = new String[1];
		directIO(SmFptrConst.SMFPTR_DIO_READ_CASHIER_NAME, null, lines);
		return lines[0];
	}

	public void writeCashierName(String value) throws JposException {
		String[] lines = new String[1];
		lines[0] = value;
		directIO(SmFptrConst.SMFPTR_DIO_WRITE_CASHIER_NAME, null, lines);
	}

	// cutMode: 0  full cut, 1  partial cut
	public void cutPaper(int cutMode) throws JposException {
		int[] data = new int[1];
		data[0] = cutMode;
		directIO(SmFptrConst.SMFPTR_DIO_CUT_PAPER, data, null);
	}

	public void setFontNumber(int value) throws JposException {
		setParameter(SmFptrConst.SMFPTR_DIO_PARAM_FONT_NUMBER, value);
	}

	public int getFontNumber() throws JposException {
		return getParameter(SmFptrConst.SMFPTR_DIO_PARAM_FONT_NUMBER);
	}

	@Override
	public void printRecItemVoid(String description, long price, int quantity,
			int vatInfo, long unitPrice, String unitName) throws JposException {
		printer.printRecItemVoid(description, price, quantity, vatInfo,
				unitPrice, unitName);
	}

	@Override
	public void printRecItemAdjustmentVoid(int adjustmentType,
			String description, long amount, int vatInfo) throws JposException {
		printer.printRecItemAdjustmentVoid(adjustmentType, description, amount,
				vatInfo);
	}

	@Override
	public void printRecItemRefund(String description, long amount,
			int quantity, int vatInfo, long unitAmount, String unitName)
			throws JposException {
		printer.printRecItemRefund(description, amount, quantity, vatInfo,
				unitAmount, unitName);
	}

	@Override
	public void printRecItemRefundVoid(String description, long amount,
			int quantity, int vatInfo, long unitAmount, String unitName)
			throws JposException {
		printer.printRecItemRefundVoid(description, amount, quantity, vatInfo,
				unitAmount, unitName);
	}
}
