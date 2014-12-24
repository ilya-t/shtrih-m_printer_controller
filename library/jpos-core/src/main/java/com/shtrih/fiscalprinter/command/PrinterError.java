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

    // Printing previous command
    public static final int E_PRINTER_PREVCOMMAND = 0x50;

    // Waiting for repeat print command
    public static final int E_PRINTER_WAITPRINT = 0x58;

    // No more EJ data available (document end reached)
    public static final int E_PRINTER_EJ_NO_MORE_DATA = 0xA9;

    private PrinterError() {
    }

    public static String getText(int code) throws Exception {
        return Localizer.getString(getLocalizerKey(code));
    }

    private static String getLocalizerKey(int code) {
        switch (code) {
            case 0x00:
                return Localizer.PrinterError00;
            case 0x01:
                return Localizer.PrinterError01;
            case 0x02:
                return Localizer.PrinterError02;
            case 0x03:
                return Localizer.PrinterError03;
            case 0x04:
                return Localizer.PrinterError04;
            case 0x05:
                return Localizer.PrinterError05;
            case 0x06:
                return Localizer.PrinterError06;
            case 0x07:
                return Localizer.PrinterError07;
            case 0x08:
                return Localizer.PrinterError08;
            case 0x09:
                return Localizer.PrinterError09;
            case 0x0A:
                return Localizer.PrinterError0A;
            case 0x0B:
                return Localizer.PrinterError0B;

            case 0x11:
                return Localizer.PrinterError11;
            case 0x12:
                return Localizer.PrinterError12;
            case 0x13:
                return Localizer.PrinterError13;
            case 0x14:
                return Localizer.PrinterError14;
            case 0x15:
                return Localizer.PrinterError15;
            case 0x16:
                return Localizer.PrinterError16;
            case 0x17:
                return Localizer.PrinterError17;
            case 0x18:
                return Localizer.PrinterError18;
            case 0x19:
                return Localizer.PrinterError19;
            case 0x1A:
                return Localizer.PrinterError1A;
            case 0x1B:
                return Localizer.PrinterError1B;
            case 0x1C:
                return Localizer.PrinterError1C;
            case 0x1D:
                return Localizer.PrinterError1D;
            case 0x1E:
                return Localizer.PrinterError1E;
            case 0x1F:
                return Localizer.PrinterError1F;

            case 0x20:
                return Localizer.PrinterError20;
            case 0x21:
                return Localizer.PrinterError21;
            case 0x22:
                return Localizer.PrinterError22;
            case 0x23:
                return Localizer.PrinterError23;
            case 0x24:
                return Localizer.PrinterError24;
            case 0x25:
                return Localizer.PrinterError25;
            case 0x26:
                return Localizer.PrinterError26;
            case 0x27:
                return Localizer.PrinterError27;
            case 0x2F:
                return Localizer.PrinterError2F;

            case 0x30:
                return Localizer.PrinterError30;
            case 0x31:
                return Localizer.PrinterError31;
            case 0x32:
                return Localizer.PrinterError32;
            case 0x33:
                return Localizer.PrinterError33;
            case 0x34:
                return Localizer.PrinterError34;
            case 0x35:
                return Localizer.PrinterError35;
            case 0x36:
                return Localizer.PrinterError36;
            case 0x37:
                return Localizer.PrinterError37;
            case 0x38:
                return Localizer.PrinterError38;
            case 0x39:
                return Localizer.PrinterError39;
            case 0x3A:
                return Localizer.PrinterError3A;
            case 0x3B:
                return Localizer.PrinterError3B;
            case 0x3C:
                return Localizer.PrinterError3C;
            case 0x3D:
                return Localizer.PrinterError3D;
            case 0x3E:
                return Localizer.PrinterError3E;
            case 0x3F:
                return Localizer.PrinterError3F;

            case 0x40:
                return Localizer.PrinterError40;
            case 0x41:
                return Localizer.PrinterError41;
            case 0x42:
                return Localizer.PrinterError42;
            case 0x43:
                return Localizer.PrinterError43;
            case 0x44:
                return Localizer.PrinterError44;
            case 0x45:
                return Localizer.PrinterError45;
            case 0x46:
                return Localizer.PrinterError46;
            case 0x47:
                return Localizer.PrinterError47;
            case 0x48:
                return Localizer.PrinterError48;
            case 0x49:
                return Localizer.PrinterError49;
            case 0x4A:
                return Localizer.PrinterError4A;
            case 0x4B:
                return Localizer.PrinterError4B;
            case 0x4C:
                return Localizer.PrinterError4C;
            case 0x4D:
                return Localizer.PrinterError4D;
            case 0x4E:
                return Localizer.PrinterError4E;
            case 0x4F:
                return Localizer.PrinterError4F;

            case 0x50:
                return Localizer.PrinterError50;
            case 0x51:
                return Localizer.PrinterError51;
            case 0x52:
                return Localizer.PrinterError52;
            case 0x53:
                return Localizer.PrinterError53;
            case 0x54:
                return Localizer.PrinterError54;
            case 0x55:
                return Localizer.PrinterError55;
            case 0x56:
                return Localizer.PrinterError56;
            case 0x57:
                return Localizer.PrinterError57;
            case 0x58:
                return Localizer.PrinterError58;
            case 0x59:
                return Localizer.PrinterError59;
            case 0x5A:
                return Localizer.PrinterError5A;
            case 0x5B:
                return Localizer.PrinterError5B;
            case 0x5C:
                return Localizer.PrinterError5C;
            case 0x5D:
                return Localizer.PrinterError5D;
            case 0x5E:
                return Localizer.PrinterError5E;
            case 0x5F:
                return Localizer.PrinterError5F;

            case 0x60:
                return Localizer.PrinterError60;
            case 0x61:
                return Localizer.PrinterError61;
            case 0x62:
                return Localizer.PrinterError62;
            case 0x63:
                return Localizer.PrinterError63;
            case 0x64:
                return Localizer.PrinterError64;
            case 0x65:
                return Localizer.PrinterError65;
            case 0x66:
                return Localizer.PrinterError66;
            case 0x67:
                return Localizer.PrinterError67;
            case 0x68:
                return Localizer.PrinterError68;
            case 0x69:
                return Localizer.PrinterError69;
            case 0x6A:
                return Localizer.PrinterError6A;
            case 0x6B:
                return Localizer.PrinterError6B;
            case 0x6C:
                return Localizer.PrinterError6C;
            case 0x6D:
                return Localizer.PrinterError6D;
            case 0x6E:
                return Localizer.PrinterError6E;
            case 0x6F:
                return Localizer.PrinterError6F;

            case 0x70:
                return Localizer.PrinterError70;
            case 0x71:
                return Localizer.PrinterError71;
            case 0x72:
                return Localizer.PrinterError72;
            case 0x73:
                return Localizer.PrinterError73;
            case 0x74:
                return Localizer.PrinterError74;
            case 0x75:
                return Localizer.PrinterError75;
            case 0x76:
                return Localizer.PrinterError76;
            case 0x77:
                return Localizer.PrinterError77;
            case 0x78:
                return Localizer.PrinterError78;
            case 0x79:
                return Localizer.PrinterError79;
            case 0x7A:
                return Localizer.PrinterError7A;
            case 0x7B:
                return Localizer.PrinterError7B;
            case 0x7C:
                return Localizer.PrinterError7C;
            case 0x7D:
                return Localizer.PrinterError7D;
            case 0x7E:
                return Localizer.PrinterError7E;
            case 0x7F:
                return Localizer.PrinterError7F;

            case 0x80:
                return Localizer.PrinterError80;
            case 0x81:
                return Localizer.PrinterError81;
            case 0x82:
                return Localizer.PrinterError82;
            case 0x83:
                return Localizer.PrinterError83;
            case 0x84:
                return Localizer.PrinterError84;
            case 0x85:
                return Localizer.PrinterError85;
            case 0x86:
                return Localizer.PrinterError86;
            case 0x87:
                return Localizer.PrinterError87;
            case 0x88:
                return Localizer.PrinterError88;
            case 0x89:
                return Localizer.PrinterError89;
            case 0x8A:
                return Localizer.PrinterError8A;
            case 0x8B:
                return Localizer.PrinterError8B;
            case 0x8C:
                return Localizer.PrinterError8C;
            case 0x8D:
                return Localizer.PrinterError8D;
            case 0x8E:
                return Localizer.PrinterError8E;
            case 0x8F:
                return Localizer.PrinterError8F;

            case 0x90:
                return Localizer.PrinterError90;
            case 0x91:
                return Localizer.PrinterError91;
            case 0x92:
                return Localizer.PrinterError92;
            case 0x93:
                return Localizer.PrinterError93;
            case 0x94:
                return Localizer.PrinterError94;
            case 0x95:
                return Localizer.PrinterError95;

            case 0xA0:
                return Localizer.PrinterErrorA0;
            case 0xA1:
                return Localizer.PrinterErrorA1;
            case 0xA2:
                return Localizer.PrinterErrorA2;
            case 0xA3:
                return Localizer.PrinterErrorA3;
            case 0xA4:
                return Localizer.PrinterErrorA4;
            case 0xA5:
                return Localizer.PrinterErrorA5;
            case 0xA6:
                return Localizer.PrinterErrorA6;
            case 0xA7:
                return Localizer.PrinterErrorA7;
            case 0xA8:
                return Localizer.PrinterErrorA8;
            case 0xA9:
                return Localizer.PrinterErrorA9;
            case 0xAA:
                return Localizer.PrinterErrorAA;

            case 0xB0:
                return Localizer.PrinterErrorB0;
            case 0xB1:
                return Localizer.PrinterErrorB1;
            case 0xB2:
                return Localizer.PrinterErrorB2;

            case 0xC0:
                return Localizer.PrinterErrorC0;
            case 0xC1:
                return Localizer.PrinterErrorC1;
            case 0xC2:
                return Localizer.PrinterErrorC2;
            case 0xC3:
                return Localizer.PrinterErrorC3;
            case 0xC4:
                return Localizer.PrinterErrorC4;
            case 0xC5:
                return Localizer.PrinterErrorC5;
            case 0xC6:
                return Localizer.PrinterErrorC6;
            case 0xC7:
                return Localizer.PrinterErrorC7;
            case 0xC8:
                return Localizer.PrinterErrorC8;

            case 0xD0:
                return Localizer.PrinterErrorD0;
            case 0xD1:
                return Localizer.PrinterErrorD1;

            case 0xE0:
                return Localizer.PrinterErrorE0;
            case 0xE1:
                return Localizer.PrinterErrorE1;
            case 0xE2:
                return Localizer.PrinterErrorE2;
            case 0xE3:
                return Localizer.PrinterErrorE3;
            case 0xE4:
                return Localizer.PrinterErrorE4;

            default:
                return Localizer.UnknownPrinterError;
        }
    }

    public static String getFullText(int code) throws Exception {
        return String.valueOf(code) + ", " + getText(code);
    }
}
