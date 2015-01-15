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
import com.shtrih.fiscalprinter.model.PrinterModel;
import com.shtrih.util.Localizer;

public class DriverHeader implements JposConst, PrinterHeader {

	private final int count = 0;
	private final SMFiscalPrinter printer;
	private final Vector header = new Vector();
	private final Vector trailer = new Vector();
	private static Logger logger = Logger.getLogger(DriverHeader.class);

	/**
	 * Creates a new instance of PrinterHeader
	 */
	public DriverHeader(SMFiscalPrinter printer) {
		this.printer = printer;
	}

	public FptrParameters getParams() throws Exception {
		return printer.getParams();
	}

	public PrinterModel getModel() throws Exception {
		return printer.getModel();
	}

	@Override
	public HeaderLine getHeaderLine(int number) throws Exception {
		checkHeaderLineNumber(number);
		return (HeaderLine) header.get(number - 1);
	}

	@Override
	public HeaderLine getTrailerLine(int number) throws Exception {
		checkTrailerLineNumber(number);
		return (HeaderLine) trailer.get(number - 1);
	}

	@Override
	public void initDevice() throws Exception {
		logger.debug("initDevice");

		setNumHeaderLines(printer.getParams().numHeaderLines);
		setNumTrailerLines(printer.getParams().numTrailerLines);

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

	@Override
	public int getNumHeaderLines() {
		return printer.getParams().numHeaderLines;
	}

	@Override
	public int getNumTrailerLines() {
		return printer.getParams().numTrailerLines;
	}

	@Override
	public void setNumHeaderLines(int numHeaderLines) throws Exception {
		printer.getParams().numHeaderLines = numHeaderLines;
		header.clear();
		for (int i = 0; i < numHeaderLines; i++) {
			header.add(new HeaderLine());
		}
	}

	@Override
	public void setNumTrailerLines(int numTrailerLines) throws Exception {
		printer.getParams().numTrailerLines = numTrailerLines;
		trailer.clear();
		for (int i = 0; i < numTrailerLines; i++) {
			trailer.add(new HeaderLine());
		}
	}

	@Override
	public void setHeaderLine(int number, String text, boolean doubleWidth)
			throws Exception {
		checkHeaderLineNumber(number);
		header.set(number - 1, new HeaderLine(text, doubleWidth));
	}

	@Override
	public void setTrailerLine(int number, String text, boolean doubleWidth)
			throws Exception {
		checkTrailerLineNumber(number);
		trailer.set(number - 1, new HeaderLine(text, doubleWidth));
	}

	public void checkHeaderLineNumber(int number) throws Exception {
		if ((number < 1) || (number > getNumHeaderLines())) {
			throw new JposException(JposConst.JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.InvalidLineNumber));
		}
	}

	public void checkTrailerLineNumber(int number) throws Exception {
		if ((number < 1) || (number > getNumTrailerLines())) {
			throw new JposException(JposConst.JPOS_E_ILLEGAL,
					Localizer.getString(Localizer.InvalidLineNumber));
		}
	}

	private int printLine(HeaderLine line) throws Exception {
		FontNumber font = printer.getParams().getFont();
		if (line.isDoubleWidth()) {
			font = FontNumber.getDoubleFont();
		}
		printer.printLine(PrinterConst.SMFP_STATION_RECJRN, line.getText(),
				font);
		return getModel().getFontHeight(font);
	}

	@Override
	public void load(XmlPropReader reader) throws Exception {
		reader.readPrinterHeader(this);
	}

	@Override
	public void save(XmlPropWriter writer) throws Exception {
		writer.writePrinterHeader(this);
	}

	@Override
	public void endDocument(String additionalHeader, String additionalTrailer)
			throws Exception {
		printTrailer(additionalTrailer);
		printHeaderBeforeCutter();
	}

	@Override
	public void beginDocument(String additionalHeader, String additionalTrailer)
			throws Exception {
		printHeaderAfterCutter(additionalHeader);
	}

	public void printTrailer(String additionalTrailer) throws Exception {
		printer.waitForPrinting();
		printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_BEFORE_TRAILER);
		printTrailer();
		printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_AFTER_TRAILER);
		if (additionalTrailer.length() > 0) {
			printer.printText(PrinterConst.SMFP_STATION_REC, additionalTrailer,
					printer.getParams().getFont());
		}
		printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_AFTER_ADDTRAILER);
		printer.waitForPrinting();
	}

	public void printHeaderBeforeCutter() throws Exception {
		printer.waitForPrinting();
		int imageHeight = 0;
		int lineHeight = printer.getLineHeight(new FontNumber(
				PrinterConst.FONT_NUMBER_NORMAL));
		int lineSpacing = printer.getLineSpacing();
		int headerHeight = getModel().getHeaderHeight();
		PrinterImage image = printer
				.getPrinterImage(SmFptrConst.SMFPTR_LOGO_BEFORE_HEADER);
		if (image != null) {
			imageHeight = image.getHeight() + lineSpacing;
		}
		if (imageHeight > headerHeight) {
			if ((getParams().logoMode == SmFptrConst.SMFPTR_LOGO_MODE_SPLIT_IMAGE)
					&& (getModel()
							.getCapParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING))) {
				int ls = printer
						.readIntParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING);
				printer.writeParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING,
						0);
				int firstLine = image.getFirstLine() + 1;
				printer.printGraphics2(firstLine, firstLine + headerHeight);
				printer.waitForPrinting();
			} else {
				printBlankSpace(headerHeight);
			}
		} else {
			int lineNumber = (headerHeight - imageHeight) / lineHeight;
			int spaceHeight = (headerHeight - imageHeight) % lineHeight;
			if (spaceHeight > 0) {
				printBlankSpace(spaceHeight);
			}
			printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_BEFORE_HEADER);
			int printedHeight = printLines(header, 1, lineNumber);
			int fontHeight = getModel().getFontHeight(new FontNumber(1));
			int lineCount = (headerHeight - printedHeight + fontHeight - 1)
					/ fontHeight;
			if (lineCount > 0) {
				printer.printStringFont(PrinterConst.SMFP_STATION_REC,
						FontNumber.getNormalFont(), " ");
			}
		}
		printer.waitForPrinting();
	}

	public void printHeaderAfterCutter(String additionalHeader)
			throws Exception {
		printer.waitForPrinting();
		int imageHeight = 0;
		int lineHeight = printer.getLineHeight(new FontNumber(
				PrinterConst.FONT_NUMBER_NORMAL));
		int lineSpacing = printer.getLineSpacing();
		int headerHeight = getModel().getHeaderHeight();
		PrinterImage image = printer
				.getPrinterImage(SmFptrConst.SMFPTR_LOGO_BEFORE_HEADER);
		if (image != null) {
			imageHeight = image.getHeight() + lineSpacing;
		}
		if (imageHeight > headerHeight) {
			if ((getParams().logoMode == SmFptrConst.SMFPTR_LOGO_MODE_SPLIT_IMAGE)
					&& (getModel()
							.getCapParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING))) {
				int ls = printer
						.readIntParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING);
				printer.writeParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING,
						0);
				int firstLine = image.getFirstLine() + 1;
				printer.printGraphics2(firstLine + headerHeight + 1,
						image.getLastLine());
				printer.writeParameter(PrinterConst.SMFP_PARAMID_LINE_SPACING,
						ls);
			} else {
				printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_BEFORE_HEADER);
			}
			printLines(trailer);
		} else {
			int lineNumber = (headerHeight - imageHeight) / lineHeight;
			printLines(header, lineNumber + 1, header.size());
			printer.printReceiptImage(SmFptrConst.SMFPTR_LOGO_AFTER_HEADER);
		}
		if (additionalHeader.length() > 0) {
			printer.printText(PrinterConst.SMFP_STATION_REC, additionalHeader,
					printer.getParams().getFont());
		}
		printer.waitForPrinting();
	}

	public void printLines(Vector lines) throws Exception {
		for (int i = 0; i < lines.size(); i++) {
			printLine((HeaderLine) lines.get(i));
		}
	}

	public int printLines(Vector lines, int num1, int num2) throws Exception {
		int result = 0;
		for (int i = num1 - 1; i < num2; i++) {
			result += printLine((HeaderLine) lines.get(i));
		}
		return result;
	}

	private void printBlankSpace(int height) throws Exception {
		int lineHeight = printer.getLineHeight(FontNumber.getNormalFont());
		int lineCount = (height + lineHeight - 1) / lineHeight;
		for (int i = 0; i < lineCount; i++) {
			printRecLine(" ");
		}
		printer.waitForPrinting();
	}

	public void printSpaceLines(int count) throws Exception {
		for (int i = 0; i < count; i++) {
			printRecLine(" ");
		}
		printer.waitForPrinting();
	}

	private boolean isCutEnabled() throws Exception {
		return ((printer.getParams().cutMode == SmFptrConst.SMFPTR_CUT_MODE_AUTO) && (getModel()
				.getCapCutter()));
	}

	private void cutPaper() throws Exception {
		if (isCutEnabled()) {
			if (getParams().cutPaperDelay != 0) {
				Thread.sleep(getParams().cutPaperDelay);
			}
			printer.cutPaper(getParams().cutType);
		}
	}

	private void printTrailer() throws Exception {
		for (int i = 1; i <= getNumTrailerLines(); i++) {
			printLine(getTrailerLine(i));
		}
		printRecLine(" ");
	}

	private void printRecLine(String line) throws Exception {
		printer.printLine(PrinterConst.SMFP_STATION_REC, line,
				FontNumber.getNormalFont());
	}

}
