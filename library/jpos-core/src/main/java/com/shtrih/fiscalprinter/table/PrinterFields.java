/*
 * PrinterFields.java
 *
 * Created on 17  2009 ., 12:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.fiscalprinter.table;

/**
 *
 * @author V.Kravtsov
 */

import java.util.Vector;

public class PrinterFields {

    private final Vector list = new Vector();

    /** Creates a new instance of PrinterFields */
    public PrinterFields() {
    }

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public void add(PrinterField item) {
        list.add(item);
    }

    public PrinterField get(int index) {
        return (PrinterField) list.get(index);
    }
}
