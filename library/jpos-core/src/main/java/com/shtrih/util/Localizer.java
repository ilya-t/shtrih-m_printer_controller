/*
 * Localizer.java
 *
 * Created on 7  2010 ., 16:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.util;

/**
 *
 * @author V.Kravtsov
 */
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Localizer {

    private static Logger logger = Logger.getLogger(Localizer.class);
    public static final String receiptDuplicationNotSupported = "ReceiptDuplicationNotSupported";
    public static final String deviceIsEnabled = "DeviceIsEnabled";
    public static final String additionalHeaderNotSupported = "AdditionalHeaderNotSupported";
    public static final String additionalTrailerNotSupported = "AdditionalTrailerNotSupported";
    public static final String changeDueTextNotSupported = "ChangeDueTextNotSupported";
    public static final String multipleContractorsNotSupported = "MultipleContractorsNotSupported";
    public static final String messageTypeNotSupported = "MessageTypeNotSupported";
    public static final String methodNotSupported = "MethodNotSupported";
    public static final String invalidParameterValue = "InvalidParameterValue";
    public static final String invalidPropertyValue = "InvalidPropertyValue";
    public static final String notPaidReceiptsNotSupported = "NotPaidReceiptsNotSupported";
    public static final String invalidFiscalReceiptType = "InvalidFiscalReceiptType";
    public static final String failedConfirmDate = "FailedConfirmDate";
    public static final String wrongPrinterState = "WrongPrinterState";
    public static final String slipStationNotPresent = "SlipStationNotPresent";
    public static final String receiptStationNotPresent = "ReceiptStationNotPresent";
    public static final String journalStationNotPresent = "JournalStationNotPresent";
    public static final String graphicsNotSupported = "GraphicsNotSupported";
    public static final String endDumpFailed = "EndDumpFailed";
    public static final String LockedTaxPassword = "LockedTaxPassword";
    public static final String ConfirmDateFailed = "ConfirmDateFailed";
    public static final String WriteDecimalPointFailed = "WriteDecimalPointFailed";
    public static final String StopTestFailed = "StopTestFailed";
    public static final String PrinterSupportesEAN13Only = "PrinterSupportesEAN13Only";
    public static final String InvalidBarcodeHeight = "InvalidBarcodeHeight";
    public static final String InvalidBarcodePrintType = "InvalidBarcodePrintType";
    public static final String InvalidAnswerLength = "InvalidAnswerLength";
    public static final String InvalidFieldValue = "InvalidFieldValue";
    public static final String NoConnection = "NoConnection";
    public static final String ReadAnswerError = "ReadAnswerError";
    public static final String WriteCommandError = "WriteCommandError";
    public static final String ReceiveTimeoutNotSupported = "ReceiveTimeoutNotSupported";
    public static final String NotImplemented = "NotImplemented";
    public static final String CommandNotFound = "CommandNotFound";
    public static final String NullDataParameter = "NullDataParameter";
    public static final String NullObjectParameter = "NullObjectParameter";
    public static final String InsufficientDataLen = "InsufficientDataLen";
    public static final String InsufficientObjectLen = "InsufficientObjectLen";
    public static final String BarcodeTypeNotSupported = "BarcodeTypeNotSupported";
    public static final String BarcodeExceedsPrintWidth = "BarcodeExceedsPrintWidth";
    public static final String FailedCancelReceipt = "FailedCancelReceipt";
    public static final String BaudrateNotSupported = "BaudrateNotSupported";
    public static final String InvalidLineNumber = "InvalidLineNumber";
    public static final String InvalidImageHeight = "InvalidImageHeight";
    public static final String InvalidImageWidth = "InvalidImageWidth";
    public static final String InvalidImageIndex = "InvalidImageIndex";
    public static final String PropNotFound = "PropNotFound";
    public static final String PrinterError00 = "PrinterError00";
    public static final String PrinterError01 = "PrinterError01";
    public static final String PrinterError02 = "PrinterError02";
    public static final String PrinterError03 = "PrinterError03";
    public static final String PrinterError04 = "PrinterError04";
    public static final String PrinterError05 = "PrinterError05";
    public static final String PrinterError06 = "PrinterError06";
    public static final String PrinterError07 = "PrinterError07";
    public static final String PrinterError08 = "PrinterError08";
    public static final String PrinterError09 = "PrinterError09";
    public static final String PrinterError0A = "PrinterError0A";
    public static final String PrinterError0B = "PrinterError0B";
    public static final String PrinterError11 = "PrinterError11";
    public static final String PrinterError12 = "PrinterError12";
    public static final String PrinterError13 = "PrinterError13";
    public static final String PrinterError14 = "PrinterError14";
    public static final String PrinterError15 = "PrinterError15";
    public static final String PrinterError16 = "PrinterError16";
    public static final String PrinterError17 = "PrinterError17";
    public static final String PrinterError18 = "PrinterError18";
    public static final String PrinterError19 = "PrinterError19";
    public static final String PrinterError1A = "PrinterError1A";
    public static final String PrinterError1B = "PrinterError1B";
    public static final String PrinterError1C = "PrinterError1C";
    public static final String PrinterError1D = "PrinterError1D";
    public static final String PrinterError1E = "PrinterError1E";
    public static final String PrinterError1F = "PrinterError1F";
    public static final String PrinterError20 = "PrinterError20";
    public static final String PrinterError21 = "PrinterError21";
    public static final String PrinterError22 = "PrinterError22";
    public static final String PrinterError23 = "PrinterError23";
    public static final String PrinterError24 = "PrinterError24";
    public static final String PrinterError25 = "PrinterError25";
    public static final String PrinterError26 = "PrinterError26";
    public static final String PrinterError27 = "PrinterError27";
    public static final String PrinterError2F = "PrinterError2F";
    public static final String PrinterError30 = "PrinterError30";
    public static final String PrinterError31 = "PrinterError31";
    public static final String PrinterError32 = "PrinterError32";
    public static final String PrinterError33 = "PrinterError33";
    public static final String PrinterError34 = "PrinterError34";
    public static final String PrinterError35 = "PrinterError35";
    public static final String PrinterError36 = "PrinterError36";
    public static final String PrinterError37 = "PrinterError37";
    public static final String PrinterError38 = "PrinterError38";
    public static final String PrinterError39 = "PrinterError39";
    public static final String PrinterError3A = "PrinterError3A";
    public static final String PrinterError3B = "PrinterError3B";
    public static final String PrinterError3C = "PrinterError3C";
    public static final String PrinterError3D = "PrinterError3D";
    public static final String PrinterError3E = "PrinterError3E";
    public static final String PrinterError3F = "PrinterError3F";
    public static final String PrinterError40 = "PrinterError40";
    public static final String PrinterError41 = "PrinterError41";
    public static final String PrinterError42 = "PrinterError42";
    public static final String PrinterError43 = "PrinterError43";
    public static final String PrinterError44 = "PrinterError44";
    public static final String PrinterError45 = "PrinterError45";
    public static final String PrinterError46 = "PrinterError46";
    public static final String PrinterError47 = "PrinterError47";
    public static final String PrinterError48 = "PrinterError48";
    public static final String PrinterError49 = "PrinterError49";
    public static final String PrinterError4A = "PrinterError4A";
    public static final String PrinterError4B = "PrinterError4B";
    public static final String PrinterError4C = "PrinterError4C";
    public static final String PrinterError4D = "PrinterError4D";
    public static final String PrinterError4E = "PrinterError4E";
    public static final String PrinterError4F = "PrinterError4F";
    public static final String PrinterError50 = "PrinterError50";
    public static final String PrinterError51 = "PrinterError51";
    public static final String PrinterError52 = "PrinterError52";
    public static final String PrinterError53 = "PrinterError53";
    public static final String PrinterError54 = "PrinterError54";
    public static final String PrinterError55 = "PrinterError55";
    public static final String PrinterError56 = "PrinterError56";
    public static final String PrinterError57 = "PrinterError57";
    public static final String PrinterError58 = "PrinterError58";
    public static final String PrinterError59 = "PrinterError59";
    public static final String PrinterError5A = "PrinterError5A";
    public static final String PrinterError5B = "PrinterError5B";
    public static final String PrinterError5C = "PrinterError5C";
    public static final String PrinterError5D = "PrinterError5D";
    public static final String PrinterError5E = "PrinterError5E";
    public static final String PrinterError5F = "PrinterError5F";
    public static final String PrinterError60 = "PrinterError60";
    public static final String PrinterError61 = "PrinterError61";
    public static final String PrinterError62 = "PrinterError62";
    public static final String PrinterError63 = "PrinterError63";
    public static final String PrinterError64 = "PrinterError64";
    public static final String PrinterError65 = "PrinterError65";
    public static final String PrinterError66 = "PrinterError66";
    public static final String PrinterError67 = "PrinterError67";
    public static final String PrinterError68 = "PrinterError68";
    public static final String PrinterError69 = "PrinterError69";
    public static final String PrinterError6A = "PrinterError6A";
    public static final String PrinterError6B = "PrinterError6B";
    public static final String PrinterError6C = "PrinterError6C";
    public static final String PrinterError6D = "PrinterError6D";
    public static final String PrinterError6E = "PrinterError6E";
    public static final String PrinterError6F = "PrinterError6F";
    public static final String PrinterError70 = "PrinterError70";
    public static final String PrinterError71 = "PrinterError71";
    public static final String PrinterError72 = "PrinterError72";
    public static final String PrinterError73 = "PrinterError73";
    public static final String PrinterError74 = "PrinterError74";
    public static final String PrinterError75 = "PrinterError75";
    public static final String PrinterError76 = "PrinterError76";
    public static final String PrinterError77 = "PrinterError77";
    public static final String PrinterError78 = "PrinterError78";
    public static final String PrinterError79 = "PrinterError79";
    public static final String PrinterError7A = "PrinterError7A";
    public static final String PrinterError7B = "PrinterError7B";
    public static final String PrinterError7C = "PrinterError7C";
    public static final String PrinterError7D = "PrinterError7D";
    public static final String PrinterError7E = "PrinterError7E";
    public static final String PrinterError7F = "PrinterError7F";
    public static final String PrinterError80 = "PrinterError80";
    public static final String PrinterError81 = "PrinterError81";
    public static final String PrinterError82 = "PrinterError82";
    public static final String PrinterError83 = "PrinterError83";
    public static final String PrinterError84 = "PrinterError84";
    public static final String PrinterError85 = "PrinterError85";
    public static final String PrinterError86 = "PrinterError86";
    public static final String PrinterError87 = "PrinterError87";
    public static final String PrinterError88 = "PrinterError88";
    public static final String PrinterError89 = "PrinterError89";
    public static final String PrinterError8A = "PrinterError8A";
    public static final String PrinterError8B = "PrinterError8B";
    public static final String PrinterError8C = "PrinterError8C";
    public static final String PrinterError8D = "PrinterError8D";
    public static final String PrinterError8E = "PrinterError8E";
    public static final String PrinterError8F = "PrinterError8F";
    public static final String PrinterError90 = "PrinterError90";
    public static final String PrinterError91 = "PrinterError91";
    public static final String PrinterError92 = "PrinterError92";
    public static final String PrinterError93 = "PrinterError93";
    public static final String PrinterError94 = "PrinterError94";
    public static final String PrinterError95 = "PrinterError95";
    public static final String PrinterErrorA0 = "PrinterErrorA0";
    public static final String PrinterErrorA1 = "PrinterErrorA1";
    public static final String PrinterErrorA2 = "PrinterErrorA2";
    public static final String PrinterErrorA3 = "PrinterErrorA3";
    public static final String PrinterErrorA4 = "PrinterErrorA4";
    public static final String PrinterErrorA5 = "PrinterErrorA5";
    public static final String PrinterErrorA6 = "PrinterErrorA6";
    public static final String PrinterErrorA7 = "PrinterErrorA7";
    public static final String PrinterErrorA8 = "PrinterErrorA8";
    public static final String PrinterErrorA9 = "PrinterErrorA9";
    public static final String PrinterErrorAA = "PrinterErrorAA";
    public static final String PrinterErrorB0 = "PrinterErrorB0";
    public static final String PrinterErrorB1 = "PrinterErrorB1";
    public static final String PrinterErrorB2 = "PrinterErrorB2";
    public static final String PrinterErrorC0 = "PrinterErrorC0";
    public static final String PrinterErrorC1 = "PrinterErrorC1";
    public static final String PrinterErrorC2 = "PrinterErrorC2";
    public static final String PrinterErrorC3 = "PrinterErrorC3";
    public static final String PrinterErrorC4 = "PrinterErrorC4";
    public static final String PrinterErrorC5 = "PrinterErrorC5";
    public static final String PrinterErrorC6 = "PrinterErrorC6";
    public static final String PrinterErrorC7 = "PrinterErrorC7";
    public static final String PrinterErrorC8 = "PrinterErrorC8";
    public static final String PrinterErrorD0 = "PrinterErrorD0";
    public static final String PrinterErrorD1 = "PrinterErrorD1";
    public static final String PrinterErrorE0 = "PrinterErrorE0";
    public static final String PrinterErrorE1 = "PrinterErrorE1";
    public static final String PrinterErrorE2 = "PrinterErrorE2";
    public static final String PrinterErrorE3 = "PrinterErrorE3";
    public static final String PrinterErrorE4 = "PrinterErrorE4";
    public static final String UnknownPrinterError = "UnknownPrinterError";
    public static final String InternalHealthCheck = "InternalHealthCheck";
    public static final String RecPaperEmpty = "RecPaperEmpty";
    public static final String RecPaperNearEnd = "RecPaperNearEnd";
    public static final String RecLeverUp = "RecLeverUp";
    public static final String JrnPaperEmpty = "JrnPaperEmpty";
    public static final String JrnPaperNearEnd = "JrnPaperNearEnd";
    public static final String JrnLeverUp = "JrnLeverUp";
    public static final String EJNearFull = "EJNearFull";
    public static final String FMOverflow = "FMOverflow";
    public static final String FMLowBattery = "FMLowBattery";
    public static final String FMLastRecordCorrupted = "FMLastRecordCorrupted";
    public static final String FMEndDayRequired = "FMEndDayRequired";
    public static final String NoErrors = "NoErrors";
    public static final String PhysicalDeviceDescription = "PhysicalDeviceDescription";
    private static Localizer instance;
    private ResourceBundle bundle = null;
    private final Properties properties = new Properties();

    /**
     * Creates a new instance of Localizer
     */
    private Localizer(String resourceName) {
        initProperties();
        loadFromResource(resourceName);
    }

    public void loadFromResource(String resourceName) {
        try {
            InputStream stream = ResourceLoader.load(resourceName);
            bundle = new PropertyResourceBundle(stream);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static String getDefaultFileName() {
        String lang = Locale.getDefault().getLanguage();
        return "shtrihjavapos_" + lang + ".properties";
    }

    public static Localizer getInstance() {
        if (instance == null) {
            instance = new Localizer(getDefaultFileName());
        }
        return instance;
    }

    public static void init(String fileName) {
        instance = new Localizer(fileName);
    }

    private String getResourceString(String key) throws Exception {
        if (bundle != null) {
            String value = bundle.getString(key);
            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
            return value;
        } else {
            return properties.getProperty(key);
        }
    }

    public static String getString(String key) {
        try {
            return getInstance().getResourceString(key);
        } catch (Exception e) {
            return key;
        }
    }

    private void add(String key, String value) {
        properties.setProperty(key, value);
    }

    private void initProperties() {
        properties.clear();
        add(receiptDuplicationNotSupported,
                "Receipt duplication is not supported");
        add(deviceIsEnabled, "Device is enabled");
        add(additionalHeaderNotSupported, "Additional header is not supported");
        add(additionalTrailerNotSupported,
                "Additional trailer is not supported");
        add(changeDueTextNotSupported, "Change due text is not supported");
        add(multipleContractorsNotSupported,
                "Multiple contractors are not supported");
        add(messageTypeNotSupported, "MessageType is not supported");
        add(methodNotSupported,
                "Method not supported for selected FiscalReceiptType");
        add(invalidParameterValue, "Invalid parameter value");
        add(invalidPropertyValue, "Invalid property value");
        add(notPaidReceiptsNotSupported, "Not paid receipts is not supported");
        add(invalidFiscalReceiptType, "Invalid FiscalReceiptType value");
        add(failedConfirmDate, "Failed to confirm date. ");
        add(wrongPrinterState, "Wrong printer state");
        add(slipStationNotPresent, "Slip station not present");
        add(receiptStationNotPresent, "Receipt station not present");
        add(journalStationNotPresent, "Journal station not present");
        add(graphicsNotSupported, "Graphics not supported");
        add(endDumpFailed, "Can not change dump state");
        add(LockedTaxPassword, "Locked for invalid tax officer password");
        add(ConfirmDateFailed, "Can not change wait date state");
        add(WriteDecimalPointFailed, "Can not change point state");
        add(StopTestFailed, "Can not change test state");
        add(PrinterSupportesEAN13Only, "Printer supportes barcode EAN-13 only");
        add(InvalidBarcodeHeight, "Barcode height <= 0");
        add(InvalidBarcodePrintType, "Invalid barcode print type");
        add(InvalidAnswerLength, "Invalid answer data length");
        add(InvalidFieldValue, "Invalid field value");
        add(NoConnection, "No connection");
        add(ReceiveTimeoutNotSupported,
                "Receive timeout is not supported by port driver");
        add(NotImplemented, "Not implemented");
        add(CommandNotFound, "Command not found");
        add(NullDataParameter, "Data parameter cannot be null");
        add(NullObjectParameter, "Object parameter cannot be null");
        add(InsufficientDataLen, "Data parameter length must be greater than ");
        add(InsufficientObjectLen,
                "Object parameter length must be greater than ");
        add(BarcodeTypeNotSupported, "Barcode type is not supported");
        add(BarcodeExceedsPrintWidth, "Barcode exceeds print width");
        add(FailedCancelReceipt, "Can not cancel receipt");
        add(BaudrateNotSupported, "Baud rate is not supported");
        add(InvalidLineNumber, "Invalid line number value");
        add(InvalidImageHeight, "Image height exceeds maximum");
        add(InvalidImageWidth, "Image width exceeds maximum");
        add(InvalidImageIndex, "Invalid image index");
        add(PropNotFound, "Property not found, ");
        add(PrinterError00, "No errors");
        add(PrinterError01, "FM: FM1 FM2 or RTC error");
        add(PrinterError02, "FM: FM1 missing");
        add(PrinterError03, "FM: FM2 missing");
        add(PrinterError04, "FM: Incorrect parameters in FM command");
        add(PrinterError05, "FM: No data requested available");
        add(PrinterError06, "FM: FM is in data output mode");
        add(PrinterError07, "FM: Invalid FM command parameters");
        add(PrinterError08, "FM: Command is not supported by FM");
        add(PrinterError09, "FM: Invalid command length");
        add(PrinterError0A, "FM: Data format is not BCD");
        add(PrinterError0B, "FM: FM memory failure");
        add(PrinterError11, "FM: License in not entered");
        add(PrinterError12, "FM: Serial number is already entered");
        add(PrinterError13, "FM: Current date less than last record date");
        add(PrinterError14, "FM: Day total area overflow");
        add(PrinterError15, "FM: Day is opened");
        add(PrinterError16, "FM: Day is closed");
        add(PrinterError17, "FM: First day number more than last day number");
        add(PrinterError18, "FM: First day date more than last day date");
        add(PrinterError19, "FM: No data available");
        add(PrinterError1A, "FM: Fiscalization area overflow");
        add(PrinterError1B, "FM: Serial number not assigned");
        add(PrinterError1C,
                "FM: There is corrupted record in the defined range");
        add(PrinterError1D, "FM: Last day record is corrupted");
        add(PrinterError1E, "FM: Fiscalizations overflow");
        add(PrinterError1F, "FM: Registers memory is missing");
        add(PrinterError20, "FM: Cash register overflow after add");
        add(PrinterError21,
                "FM: Subtracted amount is more then cash register value");
        add(PrinterError22, "FM: Invalid date");
        add(PrinterError23, "FM: No activation record available");
        add(PrinterError24, "FM: Activation area overflow");
        add(PrinterError25, "FM: Activation with specified number not found");
        add(PrinterError26, "FM: More than 3 records corrupted");
        add(PrinterError27, "FM: Check sum error");
        add(PrinterError2F, "EJ: Answer raeding timeout");
        add(PrinterError30, "EJ: NAK returned");
        add(PrinterError31, "EJ: Format error");
        add(PrinterError32, "EJ: CRC error");
        add(PrinterError33, "Incorrect command parameters");
        add(PrinterError34, "No data available");
        add(PrinterError35, "Settings: Incorrect command parameters");
        add(PrinterError36, "Model: Incorrect command parameters");
        add(PrinterError37, "Command is not supported");
        add(PrinterError38, "PROM error");
        add(PrinterError39, "Internal software error");
        add(PrinterError3A, "Day charge total overflow");
        add(PrinterError3B, "Day total overflow");
        add(PrinterError3C, "EJ: Invalid registration number");
        add(PrinterError3D, "Day closed, operation is invalid");
        add(PrinterError3E, "Day departments amount overflow");
        add(PrinterError3F, "Day discount total overflow");
        add(PrinterError40, "Discount range error");
        add(PrinterError41, "Cash amount range overflow");
        add(PrinterError42, "Pay type 2 amount range overflow");
        add(PrinterError43, "Pay type 3 amount range overflow");
        add(PrinterError44, "Pay type 4 amount range overflow");
        add(PrinterError45, "Payment total less than receipt total");
        add(PrinterError46, "No enough cash in ECR");
        add(PrinterError47, "Day tax accumulator overflow");
        add(PrinterError48, "Receipt total overflow");
        add(PrinterError49, "Receipt is opened. Command is invalid");
        add(PrinterError4A, "Receipt is opened. Command is invalid");
        add(PrinterError4B, "Receipt buffer overflow");
        add(PrinterError4C, "Dayly tax turnover accumulator overflow");
        add(PrinterError4D, "Cashless amount is greater than receipt total");
        add(PrinterError4E, "Day exceedes 24 hours");
        add(PrinterError4F, "Invalid password");
        add(PrinterError50, "Printing previous command");
        add(PrinterError51, "Day cash accumulator overflow");
        add(PrinterError52, "Day payment type 2 accumulator overflow");
        add(PrinterError53, "Day payment type 3 accumulator overflow");
        add(PrinterError54, "Day payment type 4 accumulator overflow");
        add(PrinterError55, "Receipt closed, operation is invalid");
        add(PrinterError56, "No document to repeat");
        add(PrinterError57, "EJ: Day count not equal to FM day count");
        add(PrinterError58, "Waiting for repeat print command");
        add(PrinterError59, "Document is opened by another operator");
        add(PrinterError5A, "Discount sum more than receipt sum");
        add(PrinterError5B, "Charge accumulator overflow");
        add(PrinterError5C, "Low power supply voltage, 24v");
        add(PrinterError5D, "Table is undefined");
        add(PrinterError5E, "Invalid command");
        add(PrinterError5F, "Negative receipt total");
        add(PrinterError60, "Overflow on multiplication");
        add(PrinterError61, "Price range overflow");
        add(PrinterError62, "Quantity range overflow");
        add(PrinterError63, "Department range overflow");
        add(PrinterError64, "FM missing");
        add(PrinterError65, "No cash in department");
        add(PrinterError66, "Department total overflow");
        add(PrinterError67, "FM connection error");
        add(PrinterError68, "Insufficient tax turnover amount");
        add(PrinterError69, "Tax turnover overflow");
        add(PrinterError6A, "Power supply error on reading I2C answer");
        add(PrinterError6B, "No receipt paper");
        add(PrinterError6C, "No journal paper");
        add(PrinterError6D, "Insufficient tax amount");
        add(PrinterError6E, "Tax amount overflow");
        add(PrinterError6F, "Daily cash out overflow");
        add(PrinterError70, "FM overflow");
        add(PrinterError71, "Cutter failure");
        add(PrinterError72, "Command not supported in this submode");
        add(PrinterError73, "Command not supported in this mode");
        add(PrinterError74, "RAM failure");
        add(PrinterError75, "Power supply failure");
        add(PrinterError76, "Printer failure: no pulse");
        add(PrinterError77, "Printer failure: no sensor");
        add(PrinterError78, "Software replaced");
        add(PrinterError79, "FM replaced");
        add(PrinterError7A, "Field cannot be edited");
        add(PrinterError7B, "Hardware failure");
        add(PrinterError7C, "Invalid date");
        add(PrinterError7D, "Invalid date format");
        add(PrinterError7E, "Invalid frame length");
        add(PrinterError7F, "Total amount overflow");
        add(PrinterError80, "FM connection error");
        add(PrinterError81, "FM connection error");
        add(PrinterError82, "FM connection error");
        add(PrinterError83, "FM connection error");
        add(PrinterError84, "Cash amount overflow");
        add(PrinterError85, "Daily sale total overflow");
        add(PrinterError86, "Daily buy total overflow");
        add(PrinterError87, "Daily return sale total overflow");
        add(PrinterError88, "Daily return buy total overflow");
        add(PrinterError89, "Daily cash in total overflow");
        add(PrinterError8A, "Receipt charge total overflow");
        add(PrinterError8B, "Receipt discount total overflow");
        add(PrinterError8C, "Negative receipt charge total");
        add(PrinterError8D, "Negative receipt discount total");
        add(PrinterError8E, "Zero receipt total");
        add(PrinterError8F, "Non fiscal printer");
        add(PrinterError90, "Field range overflow");
        add(PrinterError91, "Print width error");
        add(PrinterError92, "Field overflow");
        add(PrinterError93, "RAM recovery successful");
        add(PrinterError94, "Receipt operation limit");
        add(PrinterError95, "Unknown electronic journal error");
        add(PrinterErrorA0, "EJ connection error");
        add(PrinterErrorA1, "EJ missing");
        add(PrinterErrorA2, "EJ: Invalid parameter or command format");
        add(PrinterErrorA3, "EJ: Invalid state");
        add(PrinterErrorA4, "EJ: Failure");
        add(PrinterErrorA5, "EJ: Cryptoprocessor failure");
        add(PrinterErrorA6, "EJ: Time limit exceeded");
        add(PrinterErrorA7, "EJ: Overflow");
        add(PrinterErrorA8, "EJ: Invalid date or time");
        add(PrinterErrorA9, "EJ: No data available");
        add(PrinterErrorAA, "EJ: Negative document total");
        add(PrinterErrorB0, "EJ: Quantity parameter overflow");
        add(PrinterErrorB1, "EJ: Amount parameter overflow");
        add(PrinterErrorB2, "EJ: Already activated");
        add(PrinterErrorC0, "Confirm date and time");
        add(PrinterErrorC1, "EJ report can not be interrupted");
        add(PrinterErrorC2, "Increased supply voltage");
        add(PrinterErrorC3, "Receipt total not equal to EJ receipt total");
        add(PrinterErrorC4, "Day number mismatch");
        add(PrinterErrorC5, "Slip buffer is empty");
        add(PrinterErrorC6, "Slip paper is missing");
        add(PrinterErrorC7, "Field is not editable in this mode");
        add(PrinterErrorC8, "Printer connection error");
        add(PrinterErrorD0, "EJ: Daily journal not printed");
        add(PrinterErrorD1, "No document in buffer");
        add(PrinterErrorE0, "Bill acceptor connection error");
        add(PrinterErrorE1, "Bill acceptor is busy");
        add(PrinterErrorE2, "Bill acceptor receipt total mismatch");
        add(PrinterErrorE3, "Bill acceptor error");
        add(PrinterErrorE4, "Bill acceptor total not zero");
        add(UnknownPrinterError, "Unknown printer error");
        add(InternalHealthCheck, "Internal health check");
        add(RecPaperEmpty, "Receipt paper is empty.");
        add(RecPaperNearEnd, "Receipt paper is near end.");
        add(RecLeverUp, "Receipt station lever is up.");
        add(JrnPaperEmpty, "Journal paper is empty.");
        add(JrnPaperNearEnd, "Journal paper is near end.");
        add(JrnLeverUp, "Journal station lever is up.");
        add(EJNearFull, "Electronic journal is near full.");
        add(FMOverflow, "Fiscal memory overflow.");
        add(FMLowBattery, "Low fiscal memory battery voltage.");
        add(FMLastRecordCorrupted, "Last fiscal memory record currupted.");
        add(FMEndDayRequired, "Fiscal memory fiscal day is over.");
        add(NoErrors, "No errors");
        add(PhysicalDeviceDescription,
                "%s,  %s, Printer firmware: %s.%d, %s, FM firmware: %s.%d, %s");
    }
}
