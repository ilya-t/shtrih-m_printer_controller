/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Enumeration;
import java.util.Vector;

import jpos.config.JposEntry;
import jpos.config.RS232Const;

import com.shtrih.fiscalprinter.FontNumber;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.jpos.JposPropertyReader;

public class FptrParameters {

	public int portType = SmFptrConst.PORT_TYPE_FROMCLASS;
	public String portClass = "com.shtrih.fiscalprinter.port.SerialPrinterPort";
	public String portName = "";
	public int baudRate = 4800;
	public int byteTimeout = 200;
	public int deviceByteTimeout = 1000;
	public boolean searchByPortEnabled = false;
	public boolean searchByBaudRateEnabled = true;
	public FontNumber font;
	public String stringEncoding = "";
	public int statusCommand = PrinterConst.SMFP_STATUS_COMMAND_11H;
	public boolean escCommandsEnabled = false;
	public static final int defaultGraphicsLineDelay = 200;
	public int graphicsLineDelay = defaultGraphicsLineDelay;
	public boolean checkTotal;
	public int department = 1;
	public int recCloseSleepTime = 0;
	public String closeReceiptText = "";
	public String subtotalText = "SUBTOTAL";
	public static final String TEXT_REC_CANCEL = "RECEIPT CANCELLED";
	public int taxPassword = 0;
	public int usrPassword = 1;
	public int sysPassword = 30;
	public String statisticFileName = "ShtrihFiscalPrinter.xml";
	public int pollInterval = 500;
	public boolean pollEnabled = true;
	public double amountFactor = 1;
	public double quantityFactor = 1;
	public boolean centerImage = true; // center BMP image
	public String fieldsFileName = "";
	public int numHeaderLines;
	public int numTrailerLines;
	public final Vector paymentNames = new Vector();
	public int reportDevice = SmFptrConst.SMFPTR_REPORT_DEVICE_EJ;
	public int reportType = PrinterConst.SMFP_REPORT_TYPE_FULL;
	public String messagesFileName = "";
	public boolean wrapText = true;
	public int recCloseWaitCount = 1;
	public int cutType = PrinterConst.SMFP_CUT_PARTIAL;
	public int maxEnqNumber = 10;
	public int maxNakCommandNumber = 3;
	public int maxNakAnswerNumber = 3;
	public int maxAckNumber = 3;
	public int maxRepeatCount = 3;
	public boolean xmlZReportEnabled = false;
	public boolean csvZReportEnabled = false;
	public String xmlZReportFileName = "ZReport.xml";
	public String csvZReportFileName = "ZReport.csv";
	public static int defaultMaxReceiptNumber = 9999;
	public int maxReceiptNumber = defaultMaxReceiptNumber;
	public static final boolean defaultAutoOpenDrawer = false;
	public boolean autoOpenDrawer = defaultAutoOpenDrawer;
	public int tableMode = SmFptrConst.SMFPTR_TABLE_MODE_AUTO;
	public int cutMode = SmFptrConst.SMFPTR_CUT_MODE_AUTO;
	public int logoMode = SmFptrConst.SMFPTR_LOGO_MODE_SPLIT_IMAGE;
	public int searchMode = SmFptrConst.SMFPTR_SEARCH_NONE;
	public String preLine = "";
	public String postLine = "";
	public boolean zeroPriceFilterEnabled = false;
	public String zeroPriceFilterTime1 = "21:00";
	public String zeroPriceFilterTime2 = "11:00";
	public String zeroPriceFilterErrorText = "";
	public boolean discountFilterEnabled = false;

	public int RFAmountLength = 8;
	public int RFQuantityLength = 10;
	public int RFSeparatorLine = SmFptrConst.SMFPTR_SEPARATOR_LINE_DASHES;
	public boolean RFShowTaxLetters = false;
	private final PayTypes payTypes = new PayTypes();
	public int salesReceiptType = SmFptrConst.SMFPTR_RECEIPT_NORMAL;
	public int cutPaperDelay = 0;
	private int monitoringPort = 50000;
	public boolean monitoringEnabled = false;
	public boolean receiptReportEnabled = false;
	public String receiptReportFileName = "ZCheckReport.xml";
	public int protocolType = SmFptrConst.SMFPTR_PROTOCOL_1;
	public int openReceiptType = SmFptrConst.SMFPTR_OPEN_RECEIPT_ITEM;
	public boolean ZReportDayNumber = false;
	public int headerMode = SmFptrConst.SMFPTR_HEADER_MODE_DRIVER;

	public FptrParameters() throws Exception {
		font = new FontNumber(PrinterConst.FONT_NUMBER_NORMAL);
	}

	public void load(JposEntry entry) throws Exception {
		if (entry != null) {
			loadPayTypes(entry);

			JposPropertyReader reader = new JposPropertyReader(entry);

			if (entry.hasPropertyWithName("portClass")) {
				portClass = (String) entry.getPropertyValue("portClass");
			}
			portType = reader.readInteger("portType",
					SmFptrConst.PORT_TYPE_FROMCLASS);
			portName = reader.readString(RS232Const.RS232_PORT_NAME_PROP_NAME,
					"");
			baudRate = reader.readInteger(RS232Const.RS232_BAUD_RATE_PROP_NAME,
					4800);
			department = reader.readInteger("department", 1);
			font = new FontNumber(reader.readInteger("fontNumber", 1));
			closeReceiptText = reader.readString("closeReceiptText", "");
			subtotalText = reader.readString("subtotalText", "SUBTOTAL");
			byteTimeout = reader.readInteger("byteTimeout", 200);
			deviceByteTimeout = reader.readInteger("deviceByteTimeout", 1000);
			taxPassword = reader.readInteger("taxPassword", 0);
			usrPassword = reader.readInteger("operatorPassword", 1);
			sysPassword = reader.readInteger("sysAdminPassword", 30);
			searchByPortEnabled = reader.readBoolean("searchByPortEnabled",
					false);
			searchByBaudRateEnabled = reader.readBoolean(
					"searchByBaudRateEnabled", true);
			pollInterval = reader.readInteger("pollInterval", 500);
			pollEnabled = reader.readBoolean("pollEnabled", true);
			amountFactor = reader.readDouble("amountFactor", 1);
			quantityFactor = reader.readDouble("quantityFactor", 1);
			stringEncoding = reader
					.readString("stringEncoding", stringEncoding);
			if (stringEncoding.equals("")) {
				stringEncoding = System.getProperty("file.encoding");
			}
			if (stringEncoding == null) {
				stringEncoding = "UTF-8";
			}
			if (stringEncoding.equals("")) {
				stringEncoding = "UTF-8";
			}

			escCommandsEnabled = reader
					.readBoolean("escCommandsEnabled", false);
			statisticFileName = reader.readString("statisticFileName",
					"ShtrihFiscalPrinter.xml");
			fieldsFileName = reader.readString("fieldsFileName", "");
			graphicsLineDelay = reader.readInteger("graphicsLineDelay",
					defaultGraphicsLineDelay);
			numHeaderLines = reader.readInteger("numHeaderLines", 5);
			numTrailerLines = reader.readInteger("numTrailerLines", 5);
			reportDevice = reader.readInteger("reportDevice",
					SmFptrConst.SMFPTR_REPORT_DEVICE_EJ);
			reportType = reader.readInteger("reportType",
					PrinterConst.SMFP_REPORT_TYPE_FULL);
			statusCommand = reader.readInteger("statusCommand",
					PrinterConst.SMFP_STATUS_COMMAND_DS);
			messagesFileName = reader.readString("messagesFileName", "");
			wrapText = reader.readBoolean("wrapText", true);
			recCloseSleepTime = reader.readInteger("recCloseSleepTime", 0);
			recCloseWaitCount = reader.readInteger("recCloseWaitCount", 1);
			cutType = reader.readInteger("cutType",
					PrinterConst.SMFP_CUT_PARTIAL);
			maxEnqNumber = reader.readInteger("maxEnqNumber", 10);
			maxNakCommandNumber = reader.readInteger("maxNakCommandNumber", 3);
			maxNakAnswerNumber = reader.readInteger("maxNakAnswerNumber", 3);
			maxAckNumber = reader.readInteger("maxAckNumber", 3);
			maxRepeatCount = reader.readInteger("maxRepeatCount", 3);
			xmlZReportEnabled = reader.readBoolean("XmlZReportEnabled", false);
			xmlZReportFileName = reader.readString("XmlZReportFileName",
					"ZReport.xml");
			csvZReportEnabled = reader.readBoolean("CsvZReportEnabled", false);
			csvZReportFileName = reader.readString("CsvZReportFileName",
					"ZReport.csv");
			maxReceiptNumber = reader.readInteger("maxReceiptNumber",
					defaultMaxReceiptNumber);
			autoOpenDrawer = reader.readBoolean("autoOpenDrawer",
					defaultAutoOpenDrawer);
			tableMode = reader.readInteger("tableMode",
					SmFptrConst.SMFPTR_TABLE_MODE_AUTO);
			cutMode = reader.readInteger("cutMode",
					SmFptrConst.SMFPTR_CUT_MODE_AUTO);
			logoMode = reader.readInteger("logoMode",
					SmFptrConst.SMFPTR_LOGO_MODE_SPLIT_IMAGE);
			searchMode = reader.readInteger("searchMode",
					SmFptrConst.SMFPTR_SEARCH_NONE);

			zeroPriceFilterEnabled = reader.readBoolean(
					"ZeroPriceFilterEnabled", false);
			zeroPriceFilterTime1 = reader.readString("ZeroPriceFilterTime1",
					"21:00");
			zeroPriceFilterTime2 = reader.readString("ZeroPriceFilterTime2",
					"11:00");
			zeroPriceFilterErrorText = reader.readString(
					"ZeroPriceFilterErrorText", "");
			discountFilterEnabled = reader.readBoolean("DiscountFilterEnabled",
					false);
			salesReceiptType = reader.readInteger("salesReceiptType",
					SmFptrConst.SMFPTR_RECEIPT_NORMAL);
			cutPaperDelay = reader.readInteger("cutPaperDelay", 0);
			RFAmountLength = reader.readInteger("RFAmountLength", 8);
			RFQuantityLength = reader.readInteger("RFQuantityLength", 10);
			monitoringPort = reader.readInteger("MonitoringPort", 50000);
			monitoringEnabled = reader.readBoolean("MonitoringEnabled", false);
			receiptReportEnabled = reader.readBoolean("receiptReportEnabled",
					false);
			receiptReportFileName = reader.readString("receiptReportFileName",
					"ZCheckReport.xml");
			protocolType = reader.readInteger("protocolType",
					SmFptrConst.SMFPTR_PROTOCOL_1);
			openReceiptType = reader.readInteger("openReceiptType",
					SmFptrConst.SMFPTR_OPEN_RECEIPT_ITEM);
			ZReportDayNumber = reader.readBoolean("ZReportDayNumber", false);

			headerMode = reader.readInteger("headerMode",
					SmFptrConst.SMFPTR_HEADER_MODE_DRIVER);

			// paymentNames
			String paymentName;
			String propertyName;
			paymentNames.clear();
			for (int i = 1; i <= 4; i++) {
				propertyName = "paymentName" + String.valueOf(i);
				if (entry.hasPropertyWithName(propertyName)) {
					paymentName = (String) entry.getPropertyValue(propertyName);
					paymentNames.add(new FptrPaymentName(i, paymentName));
				}
			}
		}
	}

	public Vector getPaymentNames() {
		return paymentNames;
	}

	public void loadPayTypes(JposEntry jposEntry) throws Exception {
		payTypes.clear();
		String payTypeID = "";
		String payTypeValue = "";
		String propertyName = "";

		Enumeration props = jposEntry.getPropertyNames();
		while (props.hasMoreElements()) {
			propertyName = (String) props.nextElement();
			if (propertyName.indexOf("payType") != -1) {
				payTypeValue = (String) jposEntry
						.getPropertyValue(propertyName);
				payTypeID = propertyName.substring(propertyName
						.indexOf("payType") + 7);
				payTypes.put(payTypeID,
						new PayType(Integer.parseInt(payTypeValue)));
			}
		}
	}

	public void setFont(FontNumber font) {
		this.font = font;
	}

	public FontNumber getFont() {
		return font;
	}

	public PayTypes getPayTypes() {
		return payTypes;
	}

	public int getMonitoringPort() {
		return monitoringPort;
	}
}
