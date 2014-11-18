/*
 * PrinterImage.java
 *
 * Created on March 21 2008, 6:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.command;

/**
 *
 * @author V.Kravtsov
 */
// javaimport com.shtrih.jpos.fiscalprinter.*;
import java.util.Arrays;
import java.util.BitSet;

import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.util.BitUtils;
import com.shtrih.util.ImageReader;

public class ImageLoader {

    private int firstLine = 0;
    private boolean centerImage = true;
    private final SMFiscalPrinter printer;

    /** Creates a new instance of PrinterImage */
    public ImageLoader(SMFiscalPrinter printer) {
        this.printer = printer;
    }

    public int getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(int value) {
        this.firstLine = value;
    }

    public void setCenterImage(boolean value) {
        this.centerImage = value;
    }

    // convert pixels to bits
    private byte[] pixelsToBits(byte[] pixels) {
        BitSet bits = new BitSet(pixels.length);
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == 0) {
                bits.set(i);
            }
        }
        return BitUtils.toByteArray(bits);
    }

    public void load(String fileName) throws Exception {
        load(new ImageReader(fileName));
    }

    public void load(ImageReader reader) throws Exception {
        int imageWidth = reader.getWidth();
        int imageHeight = reader.getHeight();
        byte[][] lines = new byte[imageHeight][];
        byte[][] imageData = reader.getData();
        for (int i = 0; i < imageHeight; i++) {
            lines[i] = pixelsToBits(imageData[i]);
            lines[i] = BitUtils.swap(lines[i]);
        }
        if (centerImage) {
            int maxWidth = printer.getModel().getMaxGraphicsWidth();
            centerImage(lines, reader.getWidth(), maxWidth);
        }
        writeImageData(lines);
    }

    private void centerImage(byte[][] data, int imageWidth, int maxWidth) {
        int offset = (maxWidth - imageWidth) / 16;
        for (int i = 0; i < data.length; i++) {
            byte[] b = new byte[offset + data[i].length];
            Arrays.fill(b, (byte) 0);
            for (int j = 0; j < data[i].length; j++) {
                b[offset + j] = data[i][j];
            }
            data[i] = b;
        }
    }

    private void writeImageData(byte[][] data) throws Exception {
        for (int i = 0; i < data.length; i++) {
            printer.loadImageData(firstLine + i, data[i]);
        }
    }

}
