/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 * @author V.Kravtsov
 */
public class PrinterParameter {

    private final int id;
    private final int tableNumber;
    private final int rowNumber;
    private final int fieldNumber;
    private final ParameterValues values = new ParameterValues();

    public PrinterParameter(int id, int tableNumber, int rowNumber,
            int fieldNumber) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.rowNumber = rowNumber;
        this.fieldNumber = fieldNumber;
    }

    public ParameterValues getValues() {
        return values;
    }

    public int getFieldValue(int value) throws Exception {
        Integer result = values.getFieldValue(value);
        if (result == null) {
            return value;
        }
        return result.intValue();
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }
}
