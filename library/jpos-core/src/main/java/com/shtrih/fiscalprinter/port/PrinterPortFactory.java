/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.port;

/**
 *
 * @author V.Kravtsov
 */

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import com.shtrih.jpos.fiscalprinter.FptrParameters;
import com.shtrih.jpos.fiscalprinter.SmFptrConst;

public class PrinterPortFactory {

    private static Logger logger = Logger.getLogger(PrinterPortFactory.class);

    private PrinterPortFactory() {
    }

    public static PrinterPort createInstance(FptrParameters params)
            throws Exception {
        PrinterPort result = null;
        switch (params.portType) {
            case SmFptrConst.PORT_TYPE_SERIAL:
                return new SerialPrinterPort();

            case SmFptrConst.PORT_TYPE_SOCKET:
                return new SocketPort();

            case SmFptrConst.PORT_TYPE_FROMCLASS:
                Class portClass = Class.forName(params.portClass);
                Class[] parameters = new Class[0];
                Constructor ctor = portClass.getConstructor(parameters);
                PrinterPort instance = (PrinterPort) ctor
                        .newInstance(parameters);
                return instance;

            default:
                throw new Exception("Invalid portType value");
        }

    }
}
