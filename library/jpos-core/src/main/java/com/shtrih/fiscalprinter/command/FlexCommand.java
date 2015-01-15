/*
 * FlexCommand.java
 *
 * Created on 19 ?????? 2009 ?., 16:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 * @author V.Kravtsov
 */
public class FlexCommand extends PrinterCommand {

    private int code; // command code
    private String text; // command name
    private int timeout; // command timeout in milliseconds
    private CommandParams inParams; // input parameters
    private CommandParams outParams; // output parameters

    /** Creates a new instance of FlexCommand */
    public FlexCommand() {
        super();
    }

    public final int getCode() {
        return code;
    }

    public final int getTimeout() {
        return timeout;
    }

    public final String getText() {
        return text;
    }

    public CommandParams getInParams() {
        return inParams;
    }

    public CommandParams getOutParams() {
        return outParams;
    }

    public void encode(CommandOutputStream out) throws Exception {
        getInParams().encode(out);
    }

    public void decode(CommandInputStream in) throws Exception {
        getOutParams().decode(in);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setInParams(CommandParams inParams) {
        this.inParams = inParams;
    }

    public void setOutParams(CommandParams outParams) {
        this.outParams = outParams;
    }
}
