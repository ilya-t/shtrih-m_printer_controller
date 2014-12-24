/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.util.Vector;

import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.fiscalprinter.command.CashRegister;
import com.shtrih.fiscalprinter.command.LongPrinterStatus;
import com.shtrih.fiscalprinter.command.OperationRegister;

public class RegisterReportReader {

    private RegisterReportReader() {
    }

    public static void execute(RegisterReport report, SMFiscalPrinter printer)
            throws Exception {
        report.dayNumber = getZReportNumber(printer);

        int result = 0;
        Vector cashRegisters = report.getCashRegisters();
        for (int i = 0; i <= 255; i++) {
            CashRegister register = new CashRegister(i);
            result = printer.readCashRegister(register);
            if (result != 0) {
                break;
            }
            cashRegisters.add(register);
        }
        Vector operRegisters = report.getOperRegisters();
        for (int i = 0; i <= 255; i++) {
            OperationRegister register = new OperationRegister(i);
            result = printer.readOperationRegister(register);
            if (result != 0) {
                break;
            }
            operRegisters.add(register);
        }
    }

    public static int getZReportNumber(SMFiscalPrinter printer)
            throws Exception {
        LongPrinterStatus status = printer.readLongStatus().getStatus();
        if (status.getRegistrationNumber() == 0) {
            return printer.readOperationRegister(159) + 1;
        } else {
            return status.getDayNumber() + 1;
        }
    }

}
