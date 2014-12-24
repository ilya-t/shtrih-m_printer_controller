/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Vector;

public class PrinterFonts {

    private final Vector list = new Vector();

    /** Creates a new instance of PrinterFonts */
    public PrinterFonts() {
    }

    public void clear() {
        list.clear();
    }

    public void add(PrinterFont item) {
        list.add(item);
    }

    public PrinterFont add(int number, int charWidth, int charHeight)
            throws Exception {
        PrinterFont font = new PrinterFont(new FontNumber(number), charWidth,
                charHeight);
        list.add(font);
        return font;
    }

    public int size() {
        return list.size();
    }

    public PrinterFont get(int index) {
        return (PrinterFont) list.get(index);
    }

    public PrinterFont itemByNumber(FontNumber number) throws Exception {
        for (int i = 0; i < size(); i++) {
            PrinterFont item = get(i);
            if (item.getNumber().isEqual(number)) {
                return item;
            }
        }
        throw new Exception("Font not found, " + String.valueOf(number));
    }
}
