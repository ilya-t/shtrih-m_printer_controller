/*
 * EncoderJBarcode.java
 *
 * Created on 27 March 2008, 11:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.barcode;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Arrays;
import java.util.BitSet;

import org.jbarcode.encode.BarSet;
import org.jbarcode.encode.BarcodeEncoder;
import org.jbarcode.encode.CodabarEncoder;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.encode.Interleaved2of5Encoder;
import org.jbarcode.encode.UPCAEncoder;
import org.jbarcode.encode.UPCEEncoder;

import com.shtrih.util.BitUtils;
import com.shtrih.util.Localizer;

public class JBarcodeEncoder implements SmBarcodeEncoder {

    /**
     * Creates a new instance of EncoderJBarcode
     */
    public JBarcodeEncoder() {
    }

    private BitSet BarsetToBits(BarSet[] bars, int barWidth) {
        int bitIndex = 0;
        BitSet bits = new BitSet();

        for (int i = 0; i < bars.length; i++) {
            BarSet barset = bars[i];
            for (int j = 0; j < barset.length(); j++) {
                boolean isBar = barset.get(j);
                for (int k = 0; k < barWidth; k++) {
                    if (isBar) {
                        bits.set(bitIndex);
                    }
                    bitIndex++;
                }
            }
        }
        return bits;
    }

    private byte[] centerGraphics(byte[] data, int len) {
        int l = (len - data.length) / 2;
        if (l < 0) {
            l = 0;
        }

        byte[] result = new byte[data.length + l];
        Arrays.fill(result, (byte) 0);
        System.arraycopy(data, (byte) 0, result, (byte) l, data.length);
        return result;
    }

    private BarcodeEncoder getEncoder(int barcodeType) throws Exception {
        switch (barcodeType) {
            case PrinterBarcode.SM_BARCODE_UPCA:
                return UPCAEncoder.getInstance();

            case PrinterBarcode.SM_BARCODE_UPCE:
                return UPCEEncoder.getInstance();

            case PrinterBarcode.SM_BARCODE_EAN8:
                return EAN8Encoder.getInstance();

            case PrinterBarcode.SM_BARCODE_EAN13:
                return EAN13Encoder.getInstance();

            case PrinterBarcode.SM_BARCODE_CODE39:
                return Code39Encoder.getInstance();

            case PrinterBarcode.SM_BARCODE_ITF:
                return Interleaved2of5Encoder.getInstance();

            case PrinterBarcode.SM_BARCODE_CODABAR:
                return CodabarEncoder.getInstance();

            case PrinterBarcode.SM_BARCODE_CODE128:
                return Code128Encoder.getInstance();

                // case BARCODE_CODE93:
                // case BARCODE_PDF417:
            default:
                throw new Exception(
                        Localizer.getString(Localizer.BarcodeTypeNotSupported));
        }
    }

    
    public byte[] encode(PrinterBarcode barcode, int printWidth)
            throws Exception {
        BarcodeEncoder encoder = getEncoder(barcode.getType());
        // BarSet[] bars = encoder.encode(barcode.data, 1);
        String code = barcode.getData();
        if (barcode.getType() != PrinterBarcode.SM_BARCODE_CODE39) {
            code = code + encoder.computeCheckSum(code);
        }

        BarSet[] barset = encoder.encode(code);

        BitSet bits = BarsetToBits(barset, barcode.getBarWidth());
        byte[] data = BitUtils.toByteArray(bits);

        int barcodeWidth = data.length * 8;
        if (barcodeWidth > printWidth) {
            throw new Exception(
                    Localizer.getString(Localizer.BarcodeExceedsPrintWidth)
                            + ", " + String.valueOf(barcodeWidth) + "> "
                            + String.valueOf(printWidth));
        }

        data = BitUtils.swap(data);
        return centerGraphics(data, printWidth / 8);
    }

    
    public byte[][] encode2D(PrinterBarcode barcode, int printWidth)
            throws Exception {
        throw new Exception("2D barcodes not supported");
    }

}
