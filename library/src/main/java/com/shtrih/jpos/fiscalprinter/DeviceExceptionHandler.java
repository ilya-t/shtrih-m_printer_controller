/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */

import com.shtrih.fiscalprinter.ExceptionHandler;

public class DeviceExceptionHandler implements ExceptionHandler {
    private final FiscalPrinterImpl service;

    public DeviceExceptionHandler(FiscalPrinterImpl service) {
        this.service = service;
    }

    public boolean handleException(Exception e) throws Exception {
        return service.handleDeviceException(e);
    }
}
