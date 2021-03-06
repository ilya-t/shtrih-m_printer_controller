package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import com.shtrih.fiscalprinter.command.CashRegister;
import com.shtrih.fiscalprinter.command.OperationRegister;

public class CsvRegisterReportWriter {

    private static final String Separator = ";";

    private CsvRegisterReportWriter() {
    }

    public static void execute(RegisterReport report, String fileName)
            throws Exception {
        if (fileName.equals("")) {
            fileName = "ZReport.csv";
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF8"));
        try {
            Vector cashRegisters = report.getCashRegisters();
            for (int i = 0; i < cashRegisters.size(); i++) {
                CashRegister cashRegister = (CashRegister) cashRegisters.get(i);

                String line = "0"
                        + Separator
                        + String.valueOf(cashRegister.getNumber())
                        + Separator
                        + String.valueOf(cashRegister.getValue())
                        + Separator
                        + String.valueOf(CashRegister.getEngName(cashRegister
                                .getNumber()));
                writer.write(line);
                writer.newLine();
            }

            Vector operRegisters = report.getOperRegisters();

            for (int i = 0; i < operRegisters.size(); i++) {
                OperationRegister operRegister = (OperationRegister) operRegisters
                        .get(i);

                String line = "1"
                        + Separator
                        + String.valueOf(operRegister.getNumber())
                        + Separator
                        + String.valueOf(operRegister.getValue())
                        + Separator
                        + String.valueOf(OperationRegister
                                .getEngName(operRegister.getNumber()));
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } finally {
            writer.close();
        }
    }
}
