/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.barcode;

/**
 * @author V.Kravtsov
 */
public interface SmBarcodeEncoder {

    public byte[] encode(PrinterBarcode barcode, int printWidth)
            throws Exception;

    public byte[][] encode2D(PrinterBarcode barcode, int printWidth)
            throws Exception;
}
