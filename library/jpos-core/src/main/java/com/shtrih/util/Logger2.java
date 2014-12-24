/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.util;

/**
 *
 * @author V.Kravtsov
 */
import org.apache.log4j.Logger;

public class Logger2 {

    private Logger2() {
    }

    private static int min(int i1, int i2) {
        if (i1 < i2) {
            return i1;
        } else {
            return i2;
        }
    }

    public static void logTx(Logger logger, byte[] data) {
        logData(logger, "-> ", data);
    }

    public static void logRx(Logger logger, byte[] data) {
        logData(logger, "<- ", data);
    }

    public static void logData(Logger logger, String prefix, byte[] data) {
        int linelen = 20;
        int count = (data.length + linelen - 1) / linelen;
        for (int i = 0; i < count; i++) {
            int len = min(linelen, data.length - linelen * i);
            byte b[] = new byte[len];
            System.arraycopy(data, i * linelen, b, 0, len);
            logger.debug(prefix + (Hex.toHex(b, b.length)).toUpperCase());
        }
    }

}
