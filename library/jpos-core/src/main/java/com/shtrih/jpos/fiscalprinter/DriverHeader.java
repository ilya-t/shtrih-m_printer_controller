/*
 * PrinterHeader.java
 *
 * Created on March 21 2008, 14:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter;

import java.util.Vector;

import jpos.JposConst;
import jpos.JposException;

import org.apache.log4j.Logger;

import com.shtrih.fiscalprinter.FontNumber;
import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.command.ReadTableInfo;
import com.shtrih.util.Localizer;

public class DriverHeader implements JposConst, PrinterHeader {

	private int count = 0;
	private final Vector list = new Vector();
	private static Logger logger = Logger.getLogger(DriverHeader.class);

	/**
	 * Creates a new instance of PrinterHeader
	 */
	public DriverHeader() {
	}

	public void clear(){
            list.clear();
        }
        
	public void setCount(int count)
        {
            this.count = count;
            for (int i=list.size();i<=count;i++)
            {
		HeaderLine headerLine = new HeaderLine();
		headerLine.setText("");
		headerLine.setDoubleWidth(false);
                list.add(headerLine);
            }
        }
                
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public HeaderLine get(int index) 
        {
		if (validIndex(index)) 
                {
                    return (HeaderLine) list.get(index);
                } else {
                    return new HeaderLine(" ", false);
                }
	}

	public void checkNumber(int number) throws Exception {
		if (!validNumber(number)) {
			throw new JposException(JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.InvalidLineNumber));
		}
	}

	@Override
	public boolean validNumber(int number) {
		return ((number >= 1) && (number <= list.size()));
	}

	public boolean validIndex(int index) {
		return ((index >= 0) && (index < list.size()));
	}
        
	@Override
	public void setLine(int lineNumber, String text, boolean doubleWidth)
			throws Exception {
		String line = text;
		HeaderLine headerLine = new HeaderLine();
		headerLine.setText(line);
		headerLine.setDoubleWidth(doubleWidth);
                setCount(lineNumber);
		list.set(lineNumber - 1, headerLine);
	}

	public void addLine(String text, boolean doubleWidth)
			throws Exception {
		HeaderLine headerLine = new HeaderLine();
		headerLine.setText(text);
		headerLine.setDoubleWidth(doubleWidth);
		list.add(headerLine);
	}
        
	public void writeLine(SMFiscalPrinter printer, int lineNumber, String text, boolean doubleWidth)
			throws Exception 
        {
            checkNumber(lineNumber);
            setLine(lineNumber, text, doubleWidth);
	}
        
	private int printHeaderLine(SMFiscalPrinter printer, HeaderLine line) throws Exception 
        {
		FontNumber font = printer.getParams().getFont();
		if (line.isDoubleWidth()) {
			font = FontNumber.getDoubleFont();
		}
		printer.printLine(PrinterConst.SMFP_STATION_RECJRN, line.getText(),
                    font);
                int lineHeight = printer.getModel().getFontHeight(font);
                return lineHeight;
                        
	}

	@Override
	public int print(SMFiscalPrinter printer, int num1, int num2) throws Exception 
        {
            int lineHeight = 0;
		for (int i = num1 - 1; i < num2; i++) {
			lineHeight += printHeaderLine(printer, get(i));
		}
            return lineHeight;
	}

	@Override
	public int print(SMFiscalPrinter printer) throws Exception 
        {
            int lengthInDots = 0;
            for (int i = 0; i < size(); i++) 
            {
                    lengthInDots += printHeaderLine(printer, get(i));
            }
            return lengthInDots;
	}

	@Override
	public void initDevice(SMFiscalPrinter printer) throws Exception {
		ReadTableInfo tableStructure = printer
				.readTableInfo(PrinterConst.SMFP_TABLE_TEXT);
		int rowCount = tableStructure.getRowCount();
		for (int row = 1; row <= rowCount; row++) {
			int result = printer.writeTable(PrinterConst.SMFP_TABLE_TEXT, row,
					1, "");
			if (printer.failed(result)) {
				break;
			}
		}
	}
}
