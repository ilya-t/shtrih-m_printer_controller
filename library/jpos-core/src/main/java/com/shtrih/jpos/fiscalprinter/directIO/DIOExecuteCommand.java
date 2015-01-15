/*
 * DIOExecuteCommand.java
 *
 * Created on 13 ??? 2010 ?., 17:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.jpos.fiscalprinter.directIO;

/**
 *
 * @author V.Kravtsov
 */

import com.shtrih.fiscalprinter.SMFiscalPrinterImpl;
import com.shtrih.fiscalprinter.command.CommandInputStream;
import com.shtrih.fiscalprinter.command.PrinterCommand;
import com.shtrih.fiscalprinter.command.RawCommand;
import com.shtrih.jpos.DIOUtils;
import com.shtrih.jpos.fiscalprinter.FiscalPrinterImpl;

public class DIOExecuteCommand {

    private final FiscalPrinterImpl service;

    /** Creates a new instance of DIOReadPaymentName */
    public DIOExecuteCommand(FiscalPrinterImpl service) {
        this.service = service;
    }

    public void execute(int[] data, Object object) throws Exception {
        DIOUtils.checkDataMinLength(data, 1);
        DIOUtils.checkObjectNotNull(object);

        int timeout = data[0];
        byte[] tx = (byte[]) ((Object[]) object)[0];
        RawCommand command = new RawCommand();
        command.setTxData(tx);
        command.setTimeout(timeout);
        service.printer.getDevice().send(command);
        byte[] rx = command.getRxData();

        CommandInputStream in = new CommandInputStream(
                SMFiscalPrinterImpl.getCharsetName());
        in.setData(rx);
        PrinterCommand.checkMinLength(in.getSize(), 2);
        int commandCode = in.readByte();
        int resultCode = in.readByte();
        service.printer.check(resultCode);

        byte[] rxdata = new byte[rx.length - 2];
        System.arraycopy(rx, 2, rxdata, 0, rx.length - 2);
        ((Object[]) object)[1] = rxdata;
    }

}
