/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 * @author V.Kravtsov
 */
public class RawCommand extends PrinterCommand {

    private int code = 0;
    private byte[] txData;
    private byte[] rxData;

    public RawCommand() {
    }

    public void setTxData(byte[] txData) {
        this.txData = txData;
        if (txData.length > 0) {
            code = txData[0];
        }
    }

    public byte[] getRxData() {
        return rxData;
    }

    public int getCode() {
        return code;
    }

    public final String getText() {
        // return codeToString(code);
        return "";
    }

    public final void encode(CommandOutputStream out) throws Exception {
        out.writeBytes(txData);
    }

    public final void decode(CommandInputStream in) throws Exception {
        rxData = in.readBytes(in.getSize());
    }

}
