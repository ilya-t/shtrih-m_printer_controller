/*
 * ReadFieldInfo.java
 *
 * Created on 2 April 2008, 20:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 *
 * @author V.Kravtsov
 */
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;

import com.shtrih.util.Localizer;
import com.shtrih.util.MethodParameter;

/****************************************************************************
 * Read field structure Command: 2EH. Length: 7 bytes.  System Administrator
 * password (4 bytes) 30  Table number (1 byte)  Field number (1 byte) Answer:
 * 2EH. Length: (44+X+X) bytes.  Result Code (1 byte)  Field name (40 bytes)
 * Field type (1 byte) 0  BIN, 1  CHAR  Number of bytes  X (1 byte)
 * Field minimum value (X bytes) for BIN-type fields only  Field maximum value
 * (X bytes) for BIN-type fields only
 ****************************************************************************/
public final class ReadFieldInfo extends PrinterCommand {
    // in params

    private int password;
    private int tableNumber;
    private int fieldNumber;
    // out
    private int fieldSize;
    private int fieldType;
    private long minValue;
    private long maxValue;
    private String fieldName;

    /**
     * Creates a new instance of ReadFieldInfo
     */
    public ReadFieldInfo() {
    }

    public void setPassword(int value) {
        this.password = value;
    }

    public void setTableNumber(int value) throws Exception {
        MethodParameter.checkRange(value, 0, 0xFF, "table number");
        this.tableNumber = value;
    }

    public void setFieldNumber(int value) throws Exception {
        MethodParameter.checkRange(value, 0, 0xFF, "field number");
        this.fieldNumber = value;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getFieldType() {
        return fieldType;
    }

    public long getMinValue() {
        return minValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isEqual(int tableNumber, int fieldNumber) {
        return (this.tableNumber == tableNumber)
                && (this.fieldNumber == fieldNumber);
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    
    public final int getCode() {
        return 0x2E;
    }

    
    public final String getText() {
        return "Read field structure";
    }

    
    public final void encode(CommandOutputStream out) throws Exception {
        out.writeInt(password);
        out.writeByte(tableNumber);
        out.writeByte(fieldNumber);
    }

    
    public final void decode(CommandInputStream in) throws Exception {
        fieldName = in.readString(40);
        fieldName = fieldName.trim();
        fieldType = in.readByte();
        fieldSize = in.readByte();
        if (fieldType == 0) {
            minValue = in.readLong(fieldSize);
            maxValue = in.readLong(fieldSize);
        }
    }

    public boolean isFieldInteger() {
        return fieldType == 0;
    }

    public boolean isFieldString() {
        return fieldType == 1;
    }

    public void checkFieldValue(String fieldValue) throws Exception {
        if (isFieldInteger()) {
            long fieldValueLong = new Integer(fieldValue).longValue();
            if ((fieldValueLong < minValue) || (fieldValueLong > maxValue)) {
                throw new InvalidParameterException(
                        Localizer.getString(Localizer.InvalidFieldValue));
            }
        }
    }

    public byte[] getBytes(String fieldValue) {
        byte[] result = new byte[fieldSize];
        long fieldValueLong = new Integer(fieldValue).longValue();

        for (int i = 0; i < fieldSize; i++) {
            result[i] = (byte) ((fieldValueLong >> i * 8) & 0xFF);
        }
        return result;
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }
        byte[] copy = new byte[newLength];
        System.arraycopy(original, from, copy, 0,
                Math.min(original.length - from, newLength));
        return copy;
    }

    public byte[] fieldToBytes(String fieldValue, String charsetName)
            throws UnsupportedEncodingException {
        if (isFieldString()) {
            return copyOfRange(fieldValue.getBytes(charsetName), 0, fieldSize);
        } else {
            return getBytes(fieldValue);
        }
    }

    public static int bytesToInt(byte[] b, int fieldSize) {
        int count = Math.min(b.length, fieldSize);
        int result = 0;
        for (int i = 0; i < count; i++) {
            int v = b[i];
            if (v < 0) {
                v = 256 + v;
            }
            result = result + (v << 8 * i);
        }
        return result;
    }

    public String bytesToField(byte[] b, String charsetName)
            throws UnsupportedEncodingException {
        String result = "";
        if (isFieldString()) {
            result = new String(b, charsetName);
            result = result.trim();
        } else {
            result = String.valueOf(bytesToInt(b, fieldSize));
        }
        return result;
    }

    
    public boolean getIsRepeatable() {
        return true;
    }
}
