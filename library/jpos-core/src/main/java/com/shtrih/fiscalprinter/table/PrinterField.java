/*
 * PrinterField.java
 *
 * Created on April 22 2008, 13:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.table;

/**
 *
 * @author V.Kravtsov
 */

import com.shtrih.fiscalprinter.command.PrinterConst;

public class PrinterField {

    private String value;
    private final int table;
    private final int row;
    private final int number;
    private final int size;
    private final int type;
    private final long min;
    private final long max;
    private final String name;

    // ????? ???????,???,????,?????? ????,??? ????,???. ????????, ????.????????,
    // ????????,????????
    /** Creates a new instance of PrinterField */
    public PrinterField(int table, int row, int number, int size, int type,
            long min, long max, String name) {
        this.table = table;
        this.row = row;
        this.number = number;
        this.size = size;
        this.type = type;
        this.min = min;
        this.max = max;
        this.name = name;
    }

    public int getTable() {
        return table;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getType() {
        return type;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isIntField() {
        return (type == PrinterConst.FIELD_TYPE_INT);
    }

    private static String SInvalidFieldValue = "Invalid field value '%s' (%d).\r\n Valid values: %d..%d";

    
        public void checkValue(String value) throws Exception {
            
        }

        /*
    public void checkValue(String value) throws Exception {
        if (isIntField()) {
            long intValue = Integer.parseInt(value);
            if ((intValue > max) || (intValue < min)) {
                String text = "Invalid field value " + "'" + name + "'" + "("
                        + String.valueOf(intValue) + ").\r\n"
                        + "Valid values: " + String.valueOf(min) + "..."
                        + String.valueOf(max);
                throw new Exception(text);
            }
        }
    }

*/
    public void setValue(String value) throws Exception {
        checkValue(value);
        this.value = value;
    }
}
