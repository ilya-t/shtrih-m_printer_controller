/*
 * XmlZReport.java
 *
 * Created on 25 ?????? 2011 ?., 18:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

/**
 *
 * @author V.Kravtsov
 */
import java.io.FileWriter;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.shtrih.fiscalprinter.command.CashRegister;
import com.shtrih.fiscalprinter.command.OperationRegister;
import com.shtrih.util.XmlUtils;

public class XmlRegisterReportWriter {

    private XmlRegisterReportWriter() {
    }

    public static void execute(RegisterReport report, String fileName)
            throws Exception {
        if (fileName.equals("")) {
            fileName = "ZReport.xml";
        }

        Vector cashRegisters = report.getCashRegisters();
        Vector operRegisters = report.getOperRegisters();

        Element node;
        Node registersNode;
        FileWriter writer = new FileWriter(fileName);
        try {
            Document xmldoc = XmlUtils.newDocument();
            Element root = xmldoc.createElement("ZReport");
            xmldoc.appendChild(root);

            registersNode = xmldoc.createElement("CashRegisters");
            root.appendChild(registersNode);
            for (int i = 0; i < cashRegisters.size(); i++) {
                CashRegister cashRegister = (CashRegister) cashRegisters.get(i);
                node = xmldoc.createElement("CashRegister");
                registersNode.appendChild(node);
                node.setAttribute("Number",
                        String.valueOf(cashRegister.getNumber()));
                node.setAttribute("Value",
                        String.valueOf(cashRegister.getValue()));

                // String name = cashRegister.getName(cashRegister.getNumber());
                // node.setAttribute("Name", name);
            }
            registersNode = xmldoc.createElement("OperationRegisters");
            root.appendChild(registersNode);
            for (int i = 0; i < operRegisters.size(); i++) {
                OperationRegister operRegister = (OperationRegister) operRegisters
                        .get(i);
                node = xmldoc.createElement("OperationRegister");
                registersNode.appendChild(node);
                node.setAttribute("Number",
                        String.valueOf(operRegister.getNumber()));
                node.setAttribute("Value",
                        String.valueOf(operRegister.getValue()));

                // String name = operRegister.getName(operRegister.getNumber());
                // node.setAttribute("Name", name);
            }
            XmlUtils.save(xmldoc, fileName);
        } finally {
            writer.close();
        }
    }
}
