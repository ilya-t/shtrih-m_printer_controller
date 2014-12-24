/*
 * ReadTableInfo.java
 *
 * Created on 2 April 2008, 19:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.fiscalprinter.command;

/**
 *
 * @author V.Kravtsov
 */

/****************************************************************************
 * Get Table Structure Command: 2DH. Length: 6 bytes.  System Administrator
 * password (4 bytes) 30  Table number (1 byte) Answer: 2DH. Length: 45 bytes.
 *  Result Code (1 byte)  Table name (40 bytes)  Number of rows (2 bytes)
 * Number of fields (1 byte)
 ****************************************************************************/

public class ReadTableInfo extends PrinterCommand {
    // in params
    private int password;
    private int tableNumber;
    // out
    private String tableName;
    private int rowCount;
    private int fieldCount;

    /**
     * Creates a new instance of ReadTableInfo
     */
    public ReadTableInfo() {
    }

    public final int getCode() {
        return 0x2D;
    }

    public final String getText() {
        return "Get table structure";
    }

    public final void encode(CommandOutputStream out) throws Exception {
        out.writeInt(getPassword());
        out.writeByte(getTableNumber());
    }

    public final void decode(CommandInputStream in) throws Exception {
        setTableName(in.readString(40));
        setTableName(getTableName().trim());
        setRowCount(in.readShort());
        setFieldCount(in.readByte());
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getTableName() {
        return tableName;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public boolean getIsRepeatable() {
        return true;
    }

    /**
     * @return the password
     */
    public int getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(int password) {
        this.password = password;
    }

    /**
     * @param tableNumber the tableNumber to set
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * @param fieldCount the fieldCount to set
     */
    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }
}
