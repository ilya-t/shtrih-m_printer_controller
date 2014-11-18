/*
 * PrinterImage.java
 *
 * Created on March 21 2008, 6:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
// java

import java.util.Arrays;

import jpos.config.JposEntry;

import org.apache.log4j.Logger;

import com.shtrih.util.ImageReader;
import com.shtrih.util.Localizer;

public class PrinterImage {

    private String fileName = "";
    private int width = 0;
    private int height = 0;
    private int firstLine = 0;
    private boolean isLoaded = false;
    private byte[][] lines = new byte[0][0]; // image data
    private static Logger logger = Logger.getLogger(PrinterImage.class);

    /**
     * Creates a new instance of PrinterImage
     */
    public PrinterImage(String fileName) {
        this.fileName = fileName;
        height = 0;
        firstLine = 0;
        isLoaded = false;
    }

    public PrinterImage() {
        this.fileName = "";
        height = 0;
        firstLine = 0;
        isLoaded = false;
    }
    
    public String getFileName() {
        return fileName;
    }

     public void  setFileName(String value) {
        this.fileName = value;
    }
     
   public int getLastLine() {
        return firstLine + height - 1;
    }

    public int getHeight() {
        return height;
    }

    public int getFirstLine() {
        return firstLine;
    }

    public boolean getIsLoaded() {
        return isLoaded;
    }

    public int getWidth() {
        return width;
    }

    public void setIsLoaded(boolean value) {
        this.isLoaded = value;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public void setFirstLine(int value) {
        this.firstLine = value;
    }

    public static PrinterImage load(String prefix, JposEntry jposEntry)
            throws Exception {
        if (!jposEntry.hasPropertyWithName(prefix + "FileName")) {
            return null;
        }
        if (!jposEntry.hasPropertyWithName(prefix + "Height")) {
            return null;
        }
        if (!jposEntry.hasPropertyWithName(prefix + "FirstLine")) {
            return null;
        }
        if (!jposEntry.hasPropertyWithName(prefix + "IsLoaded")) {
            return null;
        }

        logger.debug("PrinterImage.load");
        String fileName = (String) jposEntry.getPropertyValue(prefix
                + "FileName");
        if (fileName.equals("")) {
            return null;
        }

        PrinterImage result = new PrinterImage(fileName);
        result.height = ((Integer) jposEntry
                .getPropertyValue(prefix + "Height")).intValue();
        result.firstLine = ((Integer) jposEntry.getPropertyValue(prefix
                + "FirstLine")).intValue();
        result.isLoaded = ((Boolean) jposEntry.getPropertyValue(prefix
                + "IsLoaded")).booleanValue();
        return result;
    }

    // read image from file
    public void readFile() throws Exception 
    {
        ImageReader reader = new ImageReader(fileName);
        lines = reader.getData();
        height = reader.getHeight();
        width = reader.getWidth();
    }

    private void centerImage(int graphicsWidth) {
        int offset = (graphicsWidth - width) / 16;
        for (int i = 0; i < height; i++) {
            byte[] b = new byte[offset + lines[i].length];
            Arrays.fill(b, (byte) 0);
            for (int j = 0; j < lines[i].length; j++) {
                b[offset + j] = lines[i][j];
            }
            lines[i] = b;
        }
    }

    public void load(FiscalPrinterImpl service) throws Exception {
        if (isLoaded) {
            return;
        }

        logger.debug("loadImage");

        if (service.getParams().centerImage) {
            centerImage(service.getMaxGraphicsWidth());
        }
        // check max image width
        if (width > service.getMaxGraphicsWidth()) {
            throw new Exception(
                    Localizer.getString(Localizer.InvalidImageWidth) + ", "
                            + String.valueOf(width) + " > "
                            + String.valueOf(service.getMaxGraphicsWidth()));
        }
        // check max image height
        int imageHeight = getFirstLine() + height - 1;
        if (imageHeight > service.getMaxGraphicsHeight()) {
            throw new Exception(
                    Localizer.getString(Localizer.InvalidImageHeight) + ", "
                            + String.valueOf(imageHeight) + " > "
                            + String.valueOf(service.getMaxGraphicsHeight()));
        }
        // write image to device
        for (int i = 0; i < height; i++) {
            service.loadGraphics(getFirstLine() + i, height, lines[i]);
        }
        isLoaded = true;
    }

}
