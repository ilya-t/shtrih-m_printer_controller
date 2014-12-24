package com.shtrih.jpos.fiscalprinter;

import jpos.JposConst;
import jpos.JposException;

import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.command.ReadTableInfo;
import com.shtrih.util.Localizer;

public class DeviceHeader implements PrinterHeader {

	private int count = 0;

	public DeviceHeader() {
	}

        public void setCount(int count){
            this.count = count;
        }
        
        public void clear(){
        }
        
	@Override
	public void initDevice(SMFiscalPrinter printer) throws Exception {
		String[] fieldValue = new String[1];
		ReadTableInfo tableStructure = printer
				.readTableInfo(PrinterConst.SMFP_TABLE_TEXT);
		int rowCount = tableStructure.getRowCount();
		for (int row = 1; row <= rowCount; row++) {
			int result = printer.readTable(PrinterConst.SMFP_TABLE_TEXT, row,
					1, fieldValue);
			if (printer.failed(result)) {
				break;
			}
			if (fieldValue[0].length() == 0) {
				result = printer.writeTable(PrinterConst.SMFP_TABLE_TEXT, row,
						1, " ");
				if (printer.failed(result)) {
					{
						break;
					}
				}
			}
		}
	}

	@Override
	// doubleWidth parameter ignored
	public void setLine(int lineNumber, String text, boolean doubleWidth)
			throws Exception {
	}

	public void addLine(String text, boolean doubleWidth)
			throws Exception{
        }
        
	public void writeLine(SMFiscalPrinter printer, int lineNumber, String text, boolean doubleWidth)
			throws Exception {
		checkLineNumber(lineNumber);
		int table = printer.getModel().getHeaderTableNumber();
		int row = printer.getModel().getHeaderTableRow();
		printer.writeTable(table, row, lineNumber, text);
	}
        
	@Override
	public int size() {
		return count;
	}

	@Override
	public boolean validNumber(int number) throws Exception {
		return ((number >= 1) && (number <= count));
	}

	public void checkLineNumber(int lineNumber) throws Exception {
		if (!validNumber(lineNumber)) {
			throw new JposException(JposConst.JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.InvalidLineNumber));
		}
	}

	@Override
	public int print(SMFiscalPrinter printer) throws Exception 
        {
            return printer.getModel().getHeaderHeight();
	}

	// printed automatically by fiscal printer
	@Override
	public int print(SMFiscalPrinter printer, int num1, int num2) throws Exception 
        {
            return printer.getModel().getHeaderHeight();
        }

	@Override
	public HeaderLine get(int index) throws Exception {
		return null;
	}
}
