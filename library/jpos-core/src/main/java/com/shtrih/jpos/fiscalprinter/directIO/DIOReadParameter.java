/*
 * DIOXMLZReport.java
 *
 * Created on 25 ?????? 2011 ?., 17:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.jpos.fiscalprinter.directIO;

import com.shtrih.jpos.fiscalprinter.FiscalPrinterImpl;

/**
 * @author V.Kravtsov
 */
public class DIOReadParameter {

    private FiscalPrinterImpl service;

    /**
     * Creates a new instance of DIOXMLZReport
     */
    public DIOReadParameter(FiscalPrinterImpl service) {
        this.service = service;
    }

    public void execute(int[] data, Object object) throws Exception {
        int parameterID = data[0];
        String parameterValue = service.readParameter(parameterID);
        ((String[]) object)[0] = parameterValue;
    }
}
