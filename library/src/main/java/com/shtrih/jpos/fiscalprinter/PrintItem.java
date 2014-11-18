/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 * @author V.Kravtsov
 */

public interface PrintItem {

    public void print(FiscalPrinterImpl printer) throws Exception;
}
