/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter;

/**
 * @author V.Kravtsov
 */
public class PrinterFont {
    private final FontNumber number;
    private final int charWidth;
    private final int charHeight;

    public PrinterFont(FontNumber number, int charWidth, int charHeight) {
        this.number = number;
        this.charWidth = charWidth;
        this.charHeight = charHeight;
    }

    public FontNumber getNumber() {
        return number;
    }

    public int getCharWidth() {
        return charWidth;
    }

    public int getCharHeight() {
        return charHeight;
    }

}
