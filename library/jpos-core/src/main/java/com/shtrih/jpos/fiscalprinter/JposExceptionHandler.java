/*
 * JposExceptionHandler.java
 *
 * Created on 14 ??????? 2010 ?., 17:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.io.IOException;

import jpos.FiscalPrinterConst;
import jpos.JposConst;
import jpos.JposException;

import org.apache.log4j.Logger;

import com.shtrih.fiscalprinter.SmFiscalPrinterException;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.command.PrinterError;

public class JposExceptionHandler implements JposConst, FiscalPrinterConst,
        PrinterConst {

    static Logger logger = Logger.getLogger(JposExceptionHandler.class);

    /**
     * Creates a new instance of JposExceptionHandler
     */
    private JposExceptionHandler() {
    }

    public static void handleException(Throwable e) throws JposException {
        JposException jposException = getJposException(e);

        logger.error("JposException. " + "ErrorCode: "
                + String.valueOf(jposException.getErrorCode()) + ", "
                + "ErrorCodeExtended: "
                + String.valueOf(jposException.getErrorCodeExtended()) + ", "
                + "'" + jposException.getMessage() + "'");

        throw jposException;
    }

    public static JposException getJposException(Throwable e) {
        if (e instanceof JposException) {
            return (JposException) e;
        }
        if (e instanceof SmFiscalPrinterException) {
            SmFiscalPrinterException p = (SmFiscalPrinterException) e;
            int errorCode = p.getCode();
            String text;
            try {
                text = PrinterError.getFullText(errorCode);
            } catch (Exception err) {
                text = String.valueOf(errorCode);
            }

            switch (errorCode) {
                case SMFP_EFPTR_NO_REC_PAPER:
                    return new JposException(JPOS_E_EXTENDED,
                            JPOS_EFPTR_REC_EMPTY, text);

                case SMFP_EFPTR_NO_JRN_PAPER:
                    return new JposException(JPOS_E_EXTENDED,
                            JPOS_EFPTR_JRN_EMPTY, text);

                case SMFP_EFPTR_NO_SLP_PAPER:
                    return new JposException(JPOS_E_EXTENDED,
                            JPOS_EFPTR_SLP_EMPTY, text);

                default:
                    return new JposException(JPOS_E_EXTENDED, errorCode + 300,
                            text);
            }
        }
        if (e instanceof IOException) {
            return new JposException(JPOS_E_TIMEOUT, e.getMessage());
        }
        return new JposException(JPOS_E_FAILURE, e.getMessage());
    }

}
