/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.barcode;

/**
 * @author V.Kravtsov
 */
public class BarcodeEncoderFactory {

    private BarcodeEncoderFactory() {
    }

    public static SmBarcodeEncoder getBarcodeEncoder() {
        return new JBarcodeEncoder();
    }

}
