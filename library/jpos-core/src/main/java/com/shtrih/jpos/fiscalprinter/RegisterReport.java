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

public class RegisterReport {

    public int dayNumber = 0;
    private final Vector cashRegisters = new Vector();
    private final Vector operRegisters = new Vector();

    public RegisterReport() {
    }

    public Vector getCashRegisters() {
        return cashRegisters;
    }

    public Vector getOperRegisters() {
        return operRegisters;
    }
}
