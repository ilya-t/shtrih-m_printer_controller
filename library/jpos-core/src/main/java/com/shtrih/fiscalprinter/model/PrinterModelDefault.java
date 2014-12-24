/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.model;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.shtrih.fiscalprinter.FontNumber;
import com.shtrih.fiscalprinter.PrinterFont;
import com.shtrih.fiscalprinter.PrinterFonts;
import com.shtrih.fiscalprinter.SMFiscalPrinterImpl;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.command.PrinterParameter;
import com.shtrih.fiscalprinter.command.PrinterParameters;

public class PrinterModelDefault implements PrinterModel {

    public static Logger logger = Logger.getLogger(PrinterModelDefault.class);
    
    private HashMap fontMap = new HashMap();
    private final int[] defTextLength = {36, 18, 36, 36, 36, 36, 36};
    private final int[] defFontHeight = {24, 24, 24, 24, 24, 24, 24};

    private final int[] defSupportedBaudRates = {2400, 4800, 9600, 19200,
        38400, 57600, 115200};
    protected int id;
    protected int modelID;
    protected int protocolVersion;
    protected int protocolSubVersion;
    protected String name;
    protected boolean capRecPresent;
    protected boolean capRecEmptySensor;
    protected boolean capRecNearEndSensor;
    protected boolean capRecLeverSensor;
    protected boolean capJrnPresent;
    protected boolean capJrnEmptySensor;
    protected boolean capJrnNearEndSensor;
    protected boolean capJrnLeverSensor;
    protected boolean capEJPresent;
    protected boolean capFMPresent;
    protected boolean capSlpPresent;
    protected boolean capSlpEmptySensor;
    protected boolean capSlpNearEndSensor;
    protected int numVatRates;
    protected int printWidth;
    protected boolean capPrintGraphicsLine;
    protected boolean capHasVatTable;
    protected boolean capCoverSensor;
    protected boolean capDoubleWidth;
    protected boolean capDuplicateReceipt;
    protected int amountDecimalPlace;
    protected int numHeaderLines;
    protected int numTrailerLines;
    protected int trailerTableNumber;
    protected int headerTableNumber;
    protected int headerTableRow;
    protected int trailerTableRow;
    protected int minHeaderLines;
    protected int minTrailerLines;
    protected boolean capFullCut;
    protected boolean capPartialCut;
    protected boolean capGraphics;
    protected boolean capGraphicsEx;
    protected boolean capPrintStringFont;
    protected boolean capShortStatus;
    protected boolean capFontMetrics;
    protected int maxGraphicsWidth;
    protected int maxGraphicsHeight;
    protected boolean capOpenReceipt;
    protected int[] textLength = new int[0];
    protected int[] fontHeight = new int[0];
    protected int[] supportedBaudRates = new int[0];
    protected int lineSpacing = 5;
    private PrinterFonts fonts = new PrinterFonts();
    private final PrinterParameters parameters = new PrinterParameters();
    protected boolean capFiscalCut = true;
    protected boolean capCashInAutoCut = false;
    protected boolean capCashOutAutoCut = false;
    protected boolean capPrintBarcode2 = false;
    private int deviceFontNormal = 1;
    private int deviceFontDouble = 2;
    private int deviceFontSmall = 3;

    /**
     * Creates a new instance of PrinterModelDefault
     */
    public PrinterModelDefault() throws Exception {
        id = PrinterConst.SMFP_MODELID_DEFAULT;
        modelID = -1;
        protocolVersion = 1;
        protocolSubVersion = 0;
        name = "Default model";
        capRecPresent = true;
        capRecEmptySensor = true;
        capRecNearEndSensor = true;
        capRecLeverSensor = true;
        capJrnPresent = true;
        capJrnEmptySensor = true;
        capJrnNearEndSensor = true;
        capJrnLeverSensor = true;
        capEJPresent = true;
        capFMPresent = true;
        capSlpPresent = false;
        capSlpEmptySensor = false;
        capSlpNearEndSensor = false;
        numVatRates = 4;
        printWidth = 432;
        capPrintGraphicsLine = false;
        capHasVatTable = true;
        capCoverSensor = true;
        capDoubleWidth = true;
        capDuplicateReceipt = true;
        amountDecimalPlace = 2;
        textLength = defTextLength;
        fontHeight = defFontHeight;
        numHeaderLines = 4;
        numTrailerLines = 3;
        trailerTableNumber = 4;
        headerTableNumber = 4;
        headerTableRow = 4;
        trailerTableRow = 1;
        minHeaderLines = 4;
        minTrailerLines = 0;
        supportedBaudRates = defSupportedBaudRates;
        capFullCut = true;
        capPartialCut = true;
        capGraphics = true;
        capGraphicsEx = true;
        capShortStatus = false;
        capFontMetrics = false;
        maxGraphicsWidth = 320;
        maxGraphicsHeight = 255;
        capOpenReceipt = false;
        capFiscalCut = true;

        fonts.clear();
        fonts.add(1, 12, 25);
        fonts.add(2, 24, 77);
        fonts.add(3, 12, 29);
        fonts.add(4, 24, 41);
        fonts.add(5, 10, 26);
        fonts.add(6, 12, 41);
        fonts.add(7, 12, 41);

        deviceFontNormal = 1;
        deviceFontDouble = 2;
        deviceFontSmall = 3;
    }

    public int getId() {
        return id;
    }

    public int getModelID() {
        return modelID;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public int getProtocolSubVersion() {
        return protocolSubVersion;
    }

    public String getName() {
        return name;
    }

    public boolean getCapRecPresent() {
        return capRecPresent;
    }

    public boolean getCapJrnPresent() {
        return capJrnPresent;
    }

    public boolean getCapRecEmptySensor() {
        return capRecEmptySensor;
    }

    public boolean getCapRecNearEndSensor() {
        return capRecNearEndSensor;
    }

    public boolean getCapRecLeverSensor() {
        return capRecLeverSensor;
    }

    public boolean getCapJrnEmptySensor() {
        return capJrnEmptySensor;
    }

    public boolean getCapJrnNearEndSensor() {
        return capJrnNearEndSensor;
    }

    public boolean getCapJrnLeverSensor() {
        return capJrnLeverSensor;
    }

    public boolean getCapEJPresent() {
        return capEJPresent;
    }

    public boolean getCapFMPresent() {
        return capFMPresent;
    }

    public boolean getCapSlpPresent() {
        return capSlpPresent;
    }

    public boolean getCapSlpEmptySensor() {
        return capSlpEmptySensor;
    }

    public boolean getCapSlpNearEndSensor() {
        return capSlpNearEndSensor;
    }

    public int getNumVatRates() {
        return numVatRates;
    }

    public int getPrintWidth() {
        return printWidth;
    }

    public boolean getCapPrintGraphicsLine() {
        return capPrintGraphicsLine;
    }

    public boolean getCapHasVatTable() {
        return capHasVatTable;
    }

    public boolean getCapCoverSensor() {
        return capCoverSensor;
    }

    public boolean getCapDoubleWidth() {
        return capDoubleWidth;
    }

    public boolean getCapDuplicateReceipt() {
        return capDuplicateReceipt;
    }

    public int getAmountDecimalPlace() {
        return amountDecimalPlace;
    }

    public int getTextLength(FontNumber font) 
    {
        int result = 36;
        int fontIndex = font.getValue() - 1;
        if ((fontIndex >= 0) && (fontIndex < textLength.length)) {
            result = textLength[fontIndex];
        }
        return result;
    }

    public int getFontHeight(FontNumber font) throws Exception {
        int result = 24;
        if (fontHeight.length == 0) {
            result = fonts.itemByNumber(font).getCharHeight();
        } else {
            int fontIndex = font.getValue() - 1;
            if ((fontIndex >= 0) && (fontIndex < fontHeight.length)) {
                result = fontHeight[fontIndex];
            }
        }
        return result;
    }

    public int[] getTextLength() {
        return textLength;
    }

    public int[] getFontHeight() {
        return fontHeight;
    }

    public int getNumHeaderLines() {
        return numHeaderLines;
    }

    public int getNumTrailerLines() {
        return numTrailerLines;
    }

    public int getTrailerTableNumber() {
        return trailerTableNumber;
    }

    public int getHeaderTableNumber() {
        return headerTableNumber;
    }

    public int getHeaderTableRow() {
        return headerTableRow;
    }

    public int getTrailerTableRow() {
        return trailerTableRow;
    }

    public int getMinHeaderLines() {
        return minHeaderLines;
    }

    public int getMinTrailerLines() {
        return minTrailerLines;
    }

    public int[] getSupportedBaudRates() {
        return supportedBaudRates;
    }

    public boolean getCapFullCut() {
        return capFullCut;
    }

    public boolean getCapPartialCut() {
        return capPartialCut;
    }

    public boolean getCapGraphics() {
        return capGraphics;
    }

    public boolean getCapGraphicsEx() {
        return capGraphicsEx;
    }

    public boolean getCapPrintStringFont() {
        return capPrintStringFont;
    }

    public boolean getCapShortStatus() {
        return capShortStatus;
    }

    public boolean getCapFontMetrics() {
        return capFontMetrics;
    }

    public int getMaxGraphicsWidth() {
        return maxGraphicsWidth;
    }

    public int getMaxGraphicsHeight() {
        return maxGraphicsHeight;
    }

    public boolean getCapOpenReceipt() {
        return capOpenReceipt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapRecPresent(boolean capRecPresent) {
        this.capRecPresent = capRecPresent;
    }

    public void setCapRecEmptySensor(boolean capRecEmptySensor) {
        this.capRecEmptySensor = capRecEmptySensor;
    }

    public void setCapRecNearEndSensor(boolean capRecNearEndSensor) {
        this.capRecNearEndSensor = capRecNearEndSensor;
    }

    public void setCapRecLeverSensor(boolean capRecLeverSensor) {
        this.capRecLeverSensor = capRecLeverSensor;
    }

    public void setCapJrnPresent(boolean capJrnPresent) {
        this.capJrnPresent = capJrnPresent;
    }

    public void setCapJrnEmptySensor(boolean capJrnEmptySensor) {
        this.capJrnEmptySensor = capJrnEmptySensor;
    }

    public void setCapJrnNearEndSensor(boolean capJrnNearEndSensor) {
        this.capJrnNearEndSensor = capJrnNearEndSensor;
    }

    public void setCapJrnLeverSensor(boolean capJrnLeverSensor) {
        this.capJrnLeverSensor = capJrnLeverSensor;
    }

    public void setCapEJPresent(boolean capEJPresent) {
        this.capEJPresent = capEJPresent;
    }

    public void setCapFMPresent(boolean capFMPresent) {
        this.capFMPresent = capFMPresent;
    }

    public void setCapSlpPresent(boolean capSlpPresent) {
        this.capSlpPresent = capSlpPresent;
    }

    public void setCapSlpEmptySensor(boolean capSlpEmptySensor) {
        this.capSlpEmptySensor = capSlpEmptySensor;
    }

    public void setCapSlpNearEndSensor(boolean capSlpNearEndSensor) {
        this.capSlpNearEndSensor = capSlpNearEndSensor;
    }

    public void setNumVatRates(int numVatRates) {
        this.numVatRates = numVatRates;
    }

    public void setPrintWidth(int printWidth) {
        this.printWidth = printWidth;
    }

    public void setCapPrintGraphicsLine(boolean capPrintGraphicsLine) {
        this.capPrintGraphicsLine = capPrintGraphicsLine;
    }

    public void setCapHasVatTable(boolean capHasVatTable) {
        this.capHasVatTable = capHasVatTable;
    }

    public void setCapCoverSensor(boolean capCoverSensor) {
        this.capCoverSensor = capCoverSensor;
    }

    public void setCapDoubleWidth(boolean capDoubleWidth) {
        this.capDoubleWidth = capDoubleWidth;
    }

    public void setCapDuplicateReceipt(boolean capDuplicateReceipt) {
        this.capDuplicateReceipt = capDuplicateReceipt;
    }

    public void setAmountDecimalPlace(int amountDecimalPlace) {
        this.amountDecimalPlace = amountDecimalPlace;
    }

    public void setTextLength(int[] textLength) {
        this.textLength = textLength;
    }

    public void setFontHeight(int[] fontHeight) {
        this.fontHeight = fontHeight;
    }

    public void setNumHeaderLines(int numHeaderLines) {
        this.numHeaderLines = numHeaderLines;
    }

    public void setNumTrailerLines(int numTrailerLines) {
        this.numTrailerLines = numTrailerLines;
    }

    public void setTrailerTableNumber(int trailerTableNumber) {
        this.trailerTableNumber = trailerTableNumber;
    }

    public void setHeaderTableNumber(int headerTableNumber) {
        this.headerTableNumber = headerTableNumber;
    }

    public void setHeaderTableRow(int headerTableRow) {
        this.headerTableRow = headerTableRow;
    }

    public void setTrailerTableRow(int trailerTableRow) {
        this.trailerTableRow = trailerTableRow;
    }

    public void setMinHeaderLines(int minHeaderLines) {
        this.minHeaderLines = minHeaderLines;
    }

    public void setMinTrailerLines(int minTrailerLines) {
        this.minTrailerLines = minTrailerLines;
    }

    public void setSupportedBaudRates(int[] supportedBaudRates) {
        this.supportedBaudRates = supportedBaudRates;
    }

    public void setCapFullCut(boolean capFullCut) {
        this.capFullCut = capFullCut;
    }

    public void setCapPartialCut(boolean capPartialCut) {
        this.capPartialCut = capPartialCut;
    }

    public void setCapGraphics(boolean capGraphics) {
        this.capGraphics = capGraphics;
    }

    public void setCapPrintStringFont(boolean capPrintStringFont) {
        this.capPrintStringFont = capPrintStringFont;
    }

    public void setCapShortStatus(boolean capShortStatus) {
        this.capShortStatus = capShortStatus;
    }

    public void setCapFontMetrics(boolean capFontMetrics) {
        this.capFontMetrics = capFontMetrics;
    }

    public void setMaxGraphicsWidth(int maxGraphicsWidth) {
        this.maxGraphicsWidth = maxGraphicsWidth;
    }

    public void setMaxGraphicsHeight(int maxGraphicsHeight) {
        this.maxGraphicsHeight = maxGraphicsHeight;
    }

    public void setCapOpenReceipt(boolean capOpenReceipt) {
        this.capOpenReceipt = capOpenReceipt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setProtocolSubVersion(int protocolSubVersion) {
        this.protocolSubVersion = protocolSubVersion;
    }

    public void setCapGraphicsEx(boolean capGraphicsEx) {
        this.capGraphicsEx = capGraphicsEx;
    }

    public PrinterParameters getParameters() {
        return parameters;
    }

    public PrinterParameter getParameter(int id) throws Exception {
        return parameters.itemByID(id);
    }

    public PrinterParameter addParameter(int id, int table, int row, int field) {
        PrinterParameter parameter = new PrinterParameter(id, table, row, field);
        parameters.add(parameter);
        return parameter;
    }

    public boolean getCapCutter() {
        return capFullCut || capPartialCut;
    }

    public boolean getCapParameter(int id) throws Exception {
        return parameters.itemByID(id) != null;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public PrinterFonts getFonts() throws Exception {
        return fonts;
    }

    public PrinterFont getFont(FontNumber font) throws Exception {
        return fonts.itemByNumber(font);
    }

    public int getFontWidth(FontNumber font) throws Exception {
        return fonts.itemByNumber(font).getCharWidth();
    }

    public int getHeaderHeight() throws Exception {
        return (minHeaderLines)
                * getFontHeight(new FontNumber(PrinterConst.FONT_NUMBER_NORMAL));
    }

    public int getTrailerHeight() throws Exception {
        return (minTrailerLines)
                * getFontHeight(new FontNumber(PrinterConst.FONT_NUMBER_NORMAL));
    }

    public boolean getCapCashInAutoCut() {
        return capCashInAutoCut;
    }

    public boolean getCapCashOutAutoCut() {
        return capCashOutAutoCut;
    }

    public void setCapCashOutAutoCut(boolean capCashOutAutoCut) {
        this.capCashOutAutoCut = capCashOutAutoCut;
    }

    public void setCapCashInAutoCut(boolean capCashInAutoCut) {
        this.capCashInAutoCut = capCashInAutoCut;
    }

    public boolean isCommandSupported(int code) {
        return true;
    }

    public boolean getCapPrintBarcode2() {
        return capPrintBarcode2;
    }

    public void setCapPrintBarcode2(boolean value) {
        capPrintBarcode2 = value;
    }

    public int getDeviceFontNormal() {
        return deviceFontNormal;
    }

    public int getDeviceFontDouble() {
        return deviceFontDouble;
    }

    public int getDeviceFontSmall() {
        return deviceFontSmall;
    }

    public void setDeviceFontNormal(int value) {
        deviceFontNormal = value;
    }

    public void setDeviceFontDouble(int value) {
        deviceFontDouble = value;
    }

    public void setDeviceFontSmall(int value) {
        deviceFontSmall = value;
    }

    public void setFonts(PrinterFonts fonts) throws Exception {
        this.fonts = fonts;
    }
}
