/*
 * DIOPrintText.java
 *
 * Created on 4 ???? 2010 ?., 13:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.jpos.fiscalprinter.directIO;

/**
 *
 * @author V.Kravtsov
 */

import com.shtrih.fiscalprinter.FontNumber;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.jpos.DIOUtils;
import com.shtrih.jpos.fiscalprinter.FiscalPrinterImpl;

public class DIOPrintText {

    private final FiscalPrinterImpl service;

    /** Creates a new instance of DIOReadSerial */
    public DIOPrintText(FiscalPrinterImpl service) {
        this.service = service;
    }

    public void execute(int[] data, Object object) throws Exception {
        DIOUtils.checkObjectNotNull(object);
        String text = (String) object;

        FontNumber font = FontNumber.getNormalFont();
        if (data != null) {
            if (data.length > 0) {
                int fontNumber = data[0];
                if (FontNumber.isValidValue(fontNumber)) {
                    font = new FontNumber(fontNumber);
                }
            }
        }
        service.getPrinter().printText(PrinterConst.SMFP_STATION_RECJRN, text,
                font);
    }
}
