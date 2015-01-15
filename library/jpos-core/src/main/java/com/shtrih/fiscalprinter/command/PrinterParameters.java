/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Vector;

public class PrinterParameters {

    private final Vector list = new Vector();

    /** Creates a new instance of PrinterParameters */
    public PrinterParameters() {
    }

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public void add(PrinterParameter item) {
        list.add(item);
    }

    public PrinterParameter get(int index) {
        return (PrinterParameter) list.get(index);
    }

    public PrinterParameter itemByID(int id) throws Exception {
        for (int i = 0; i < size(); i++) {
            PrinterParameter result = get(i);
            if (result.getId() == id) {
                return result;
            }
        }
        return null;
    }
}
