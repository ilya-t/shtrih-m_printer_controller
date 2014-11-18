package com.shtrih.jpos.fiscalprinter;

import com.shtrih.fiscalprinter.SMFiscalPrinter;

public interface PrinterHeader {

	public void clear();
        
	public int size();

	public void setCount(int count);
        
	public int print(SMFiscalPrinter printer) throws Exception;

	public void initDevice(SMFiscalPrinter printer) throws Exception;

	public HeaderLine get(int index) throws Exception;

	public int print(SMFiscalPrinter printer, int num1, int num2) throws Exception;

	public boolean validNumber(int number) throws Exception;

	public void setLine(int number, String text, boolean doubleWidth)
			throws Exception;
        
	public void writeLine(SMFiscalPrinter printer, int number, String text, boolean doubleWidth)
			throws Exception;
        
	public void addLine(String text, boolean doubleWidth)
			throws Exception;

}
