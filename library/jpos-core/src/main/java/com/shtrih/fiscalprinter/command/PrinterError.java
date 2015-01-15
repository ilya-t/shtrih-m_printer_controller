/*
 * PrinterError.java
 *
 * Created on August 28 2007, 10:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author V.Kravtsov
 */

package com.shtrih.fiscalprinter.command;

import com.shtrih.util.Localizer;

public class PrinterError {

	private PrinterError() {
	}

	public static String getText(int code) throws Exception {
		String key = String.format("PrinterError%2X", code);
		String result = Localizer.getString(key);
		if (result.equals(key)) {
			result = Localizer.UnknownPrinterError;
		}
		return result;
	}

	public static String getFullText(int code) throws Exception {
		return String.valueOf(code) + ", " + getText(code);
	}
}
