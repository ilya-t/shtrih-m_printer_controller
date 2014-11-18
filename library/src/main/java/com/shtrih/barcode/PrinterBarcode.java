/*
 * PrinterBarcode.java
 *
 * Created on March 4 2008, 21:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.barcode;

import com.shtrih.jpos.fiscalprinter.FiscalPrinterImpl;
import com.shtrih.jpos.fiscalprinter.PrintItem;

/**
 * @author V.Kravtsov
 */
public class PrinterBarcode implements PrintItem {

    // ///////////////////////////////////////////////////////////////////
    // PrinterBarcode types
    // ///////////////////////////////////////////////////////////////////
    public static final int SM_BARCODE_UPCA = 0;
    public static final int SM_BARCODE_UPCE = 1;
    public static final int SM_BARCODE_EAN13 = 2;
    public static final int SM_BARCODE_EAN8 = 3;
    public static final int SM_BARCODE_CODE39 = 4;
    public static final int SM_BARCODE_ITF = 5;
    public static final int SM_BARCODE_CODABAR = 6;
    public static final int SM_BARCODE_CODE93 = 7;
    public static final int SM_BARCODE_CODE128 = 8;
    public static final int SM_BARCODE_PDF417 = 9;
    public static final int SM_BARCODE_GS1_OMNI = 11;
    public static final int SM_BARCODE_GS1_TRUNC = 12;
    public static final int SM_BARCODE_GS1_LIMIT = 13;
    public static final int SM_BARCODE_GS1_EXP = 14;
    public static final int SM_BARCODE_GS1_STK = 15;
    public static final int SM_BARCODE_GS1_STK_OMNI = 16;
    public static final int SM_BARCODE_GS1_EXP_STK = 17;
    public static final int SM_BARCODE_AZTEC = 18;
    public static final int SM_BARCODE_DATA_MATRIX = 19;
    public static final int SM_BARCODE_MAXICODE = 20;
    public static final int SM_BARCODE_QR_CODE = 21;
    public static final int SM_BARCODE_RSS_14 = 22;
    public static final int SM_BARCODE_RSS_EXPANDED = 23;
    public static final int SM_BARCODE_UPC_EAN_EXTENSION = 24;

    // ///////////////////////////////////////////////////////////////////
    // Barcode text position constants
    // ///////////////////////////////////////////////////////////////////
    /** Not printed **/
    public static final int SM_TEXTPOS_NOTPRINTED = 0;
    /** Above the bar code **/
    public static final int SM_TEXTPOS_ABOVE = 1;
    /** Below the bar code **/
    public static final int SM_TEXTPOS_BELOW = 2;
    /** Both above and below the bar code **/
    public static final int SM_TEXTPOS_BOTH = 3;
    // barcode data
    private final String data;
    // barcode label
    private final String label;
    // barcode bar width, pixels
    private final int barWidth;
    // barcode height, pixels
    private final int height;
    // barcode type
    private final int type;
    // text position
    private final int textPosition;
    // text font
    private final int textFont;
    // print type
    private final int printType;
    // aspect ratio
    private final int aspectRatio;

    /**
     * Creates a new instance of PrinterBarcode
     */
    public PrinterBarcode(String data, String label, int barWidth, int height,
            int type, int textPosition, int textFont, int printType,
            int aspectRatio) {
        this.data = data;
        this.label = label;
        this.barWidth = barWidth;
        this.height = height;
        this.type = type;
        this.textPosition = textPosition;
        this.textFont = textFont;
        this.printType = printType;
        this.aspectRatio = aspectRatio;
    }

    public String getData() {
        return data;
    }

    public String getLabel() {
        return label;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public int getTextFont() {
        return textFont;
    }

    public int getPrintType() {
        return printType;
    }

    public int getAspectRatio() {
        return aspectRatio;
    }

    public boolean isLinear() {
        return (type == SM_BARCODE_UPCA) || (type == SM_BARCODE_UPCE)
                || (type == SM_BARCODE_EAN13) || (type == SM_BARCODE_EAN8)
                || (type == SM_BARCODE_CODE39) || (type == SM_BARCODE_ITF)
                || (type == SM_BARCODE_CODABAR) || (type == SM_BARCODE_CODE93)
                || (type == SM_BARCODE_CODE128);
    }

    public boolean isTextAbove() {
        return (textPosition == SM_TEXTPOS_ABOVE)
                || (textPosition == SM_TEXTPOS_BOTH);
    }

    public boolean isTextBelow() {
        return (textPosition == SM_TEXTPOS_BELOW)
                || (textPosition == SM_TEXTPOS_BOTH);
    }

    public void print(FiscalPrinterImpl printer) throws Exception {
        printer.printer.printBarcode(this);
    }
}
