/*
 * PrinterImages.java
 *
 * Created on 12  2009 ., 12:44
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
import java.util.Vector;

import jpos.JposConst;
import jpos.JposException;

import com.shtrih.util.Localizer;

public class PrinterImages implements JposConst {

    private int maxSize = 0;
    private int totalSize = 0;
    private final Vector list = new Vector();

    /**
     * Creates a new instance of PrinterImages
     */
    public PrinterImages() {
    }

    public void clear() {
        list.clear();
        totalSize = 0;
    }

    public int size() {
        return list.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setMaxSize(int value) {
        maxSize = value;
    }

    public boolean validIndex(int index) {
        return (index >= 0) && (index < list.size());
    }

    public void checkIndex(int index) throws Exception {
        if (!validIndex(index)) {
            throw new JposException(JPOS_E_FAILURE,
                    Localizer.getString(Localizer.InvalidImageIndex));
        }
    }

    public PrinterImage get(int index) throws Exception {
        checkIndex(index);
        return (PrinterImage) list.get(index);
    }

    public PrinterImage find(String fileName) throws Exception {
        PrinterImage item = null;
        for (int i = 0; i < size(); i++) {
            item = get(i);
            if (item.getFileName().equals(fileName)) {
                return item;
            }
        }
        return null;
    }

    public int add(PrinterImage image) throws Exception {
        PrinterImage oldImage = find(image.getFileName());
        if (oldImage == null)
        {
            if ((image.getHeight() + totalSize) > maxSize) {
                throw new Exception("Total images size more than maximum size.");
            }
            image.setFirstLine(totalSize + 1);
            totalSize += image.getHeight();
            list.add(image);
            return list.size() - 1;
        } else {
            image.setFirstLine(oldImage.getFirstLine());
            image.setIsLoaded(oldImage.getIsLoaded());
            image.setHeight(oldImage.getHeight());
            int index = list.indexOf(oldImage);
            list.remove(index);
            list.insertElementAt(image, index);
            return index;
        }
    }

    public int insert(PrinterImage image) throws Exception {
        list.add(image);
        return list.size() - 1;
    }

    public int getIndex(PrinterImage image) {
        return list.indexOf(image);
    }
}
