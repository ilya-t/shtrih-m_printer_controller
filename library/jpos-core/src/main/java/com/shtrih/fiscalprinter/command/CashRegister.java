/*
 * CashRegister.java
 *
 * Created on 11  2010 ., 17:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.shtrih.fiscalprinter.command;

/**
 * @author V.Kravtsov
 */
public class CashRegister {

    private final int number;
    private long value = 0;

    public CashRegister(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public static String getName(int number) {
        return getRusName(number);
    }

    private static String SCashRegisterRus00 = "   1   ";
    private static String SCashRegisterRus01 = "   1   ";
    private static String SCashRegisterRus02 = "    1   ";
    private static String SCashRegisterRus03 = "    1   ";
    private static String SCashRegisterRus04 = "   2   ";
    private static String SCashRegisterRus05 = "   2   ";
    private static String SCashRegisterRus06 = "    2   ";
    private static String SCashRegisterRus07 = "    2   ";
    private static String SCashRegisterRus08 = "   3   ";
    private static String SCashRegisterRus09 = "   3   ";
    private static String SCashRegisterRus0A = "    3   ";
    private static String SCashRegisterRus0B = "    3   ";
    private static String SCashRegisterRus0C = "   4   ";
    private static String SCashRegisterRus0D = "   4   ";
    private static String SCashRegisterRus0E = "    4   ";
    private static String SCashRegisterRus0F = "    4   ";
    private static String SCashRegisterRus10 = "   5   ";
    private static String SCashRegisterRus11 = "   5   ";
    private static String SCashRegisterRus12 = "    5   ";
    private static String SCashRegisterRus13 = "    5   ";
    private static String SCashRegisterRus14 = "   6   ";
    private static String SCashRegisterRus15 = "   6   ";
    private static String SCashRegisterRus16 = "    6   ";
    private static String SCashRegisterRus17 = "    6   ";
    private static String SCashRegisterRus18 = "   7   ";
    private static String SCashRegisterRus19 = "   7   ";
    private static String SCashRegisterRus1A = "    7   ";
    private static String SCashRegisterRus1B = "    7   ";
    private static String SCashRegisterRus1C = "   8   ";
    private static String SCashRegisterRus1D = "   8   ";
    private static String SCashRegisterRus1E = "    8   ";
    private static String SCashRegisterRus1F = "    8   ";
    private static String SCashRegisterRus20 = "   9   ";
    private static String SCashRegisterRus21 = "   9   ";
    private static String SCashRegisterRus22 = "    9   ";
    private static String SCashRegisterRus23 = "    9   ";
    private static String SCashRegisterRus24 = "   10   ";
    private static String SCashRegisterRus25 = "   10   ";
    private static String SCashRegisterRus26 = "    10   ";
    private static String SCashRegisterRus27 = "    10   ";
    private static String SCashRegisterRus28 = "   11   ";
    private static String SCashRegisterRus29 = "   11   ";
    private static String SCashRegisterRus2A = "    11   ";
    private static String SCashRegisterRus2B = "    11   ";
    private static String SCashRegisterRus2C = "   12   ";
    private static String SCashRegisterRus2D = "   12   ";
    private static String SCashRegisterRus2E = "    12   ";
    private static String SCashRegisterRus2F = "    12   ";
    private static String SCashRegisterRus30 = "   13   ";
    private static String SCashRegisterRus31 = "   13   ";
    private static String SCashRegisterRus32 = "    13   ";
    private static String SCashRegisterRus33 = "    13   ";
    private static String SCashRegisterRus34 = "   14   ";
    private static String SCashRegisterRus35 = "   14   ";
    private static String SCashRegisterRus36 = "    14   ";
    private static String SCashRegisterRus37 = "    14   ";
    private static String SCashRegisterRus38 = "   15   ";
    private static String SCashRegisterRus39 = "   15   ";
    private static String SCashRegisterRus3A = "    15   ";
    private static String SCashRegisterRus3B = "    15   ";
    private static String SCashRegisterRus3C = "   16   ";
    private static String SCashRegisterRus3D = "   16   ";
    private static String SCashRegisterRus3E = "    16   ";
    private static String SCashRegisterRus3F = "    16   ";
    private static String SCashRegisterRus40 = "     ";
    private static String SCashRegisterRus41 = "     ";
    private static String SCashRegisterRus42 = "      ";
    private static String SCashRegisterRus43 = "      ";
    private static String SCashRegisterRus44 = "     ";
    private static String SCashRegisterRus45 = "     ";
    private static String SCashRegisterRus46 = "      ";
    private static String SCashRegisterRus47 = "      ";
    private static String SCashRegisterRus48 = "     ";
    private static String SCashRegisterRus49 = "     ";
    private static String SCashRegisterRus4A = "      ";
    private static String SCashRegisterRus4B = "      ";
    private static String SCashRegisterRus4C = "     2  ";
    private static String SCashRegisterRus4D = "     2  ";
    private static String SCashRegisterRus4E = "      2  ";
    private static String SCashRegisterRus4F = "      2  ";
    private static String SCashRegisterRus50 = "     3  ";
    private static String SCashRegisterRus51 = "     3  ";
    private static String SCashRegisterRus52 = "      3  ";
    private static String SCashRegisterRus53 = "      3  ";
    private static String SCashRegisterRus54 = "     4  ";
    private static String SCashRegisterRus55 = "     4  ";
    private static String SCashRegisterRus56 = "      4  ";
    private static String SCashRegisterRus57 = "      4  ";
    private static String SCashRegisterRus58 = "       ";
    private static String SCashRegisterRus59 = "       ";
    private static String SCashRegisterRus5A = "        ";
    private static String SCashRegisterRus5B = "        ";
    private static String SCashRegisterRus5C = "       ";
    private static String SCashRegisterRus5D = "       ";
    private static String SCashRegisterRus5E = "        ";
    private static String SCashRegisterRus5F = "        ";
    private static String SCashRegisterRus60 = "       ";
    private static String SCashRegisterRus61 = "       ";
    private static String SCashRegisterRus62 = "        ";
    private static String SCashRegisterRus63 = "        ";
    private static String SCashRegisterRus64 = "       ";
    private static String SCashRegisterRus65 = "       ";
    private static String SCashRegisterRus66 = "        ";
    private static String SCashRegisterRus67 = "        ";
    private static String SCashRegisterRus68 = "       ";
    private static String SCashRegisterRus69 = "       ";
    private static String SCashRegisterRus6A = "        ";
    private static String SCashRegisterRus6B = "        ";
    private static String SCashRegisterRus6C = "       ";
    private static String SCashRegisterRus6D = "       ";
    private static String SCashRegisterRus6E = "        ";
    private static String SCashRegisterRus6F = "        ";
    private static String SCashRegisterRus70 = "       ";
    private static String SCashRegisterRus71 = "       ";
    private static String SCashRegisterRus72 = "        ";
    private static String SCashRegisterRus73 = "        ";
    private static String SCashRegisterRus74 = "       ";
    private static String SCashRegisterRus75 = "       ";
    private static String SCashRegisterRus76 = "        ";
    private static String SCashRegisterRus77 = "        ";
    private static String SCashRegisterRus78 = "      ";
    private static String SCashRegisterRus79 = "   1   ";
    private static String SCashRegisterRus7A = "   1   ";
    private static String SCashRegisterRus7B = "    1   ";
    private static String SCashRegisterRus7C = "    1   ";
    private static String SCashRegisterRus7D = "   2   ";
    private static String SCashRegisterRus7E = "   2   ";
    private static String SCashRegisterRus7F = "    2   ";
    private static String SCashRegisterRus80 = "    2   ";
    private static String SCashRegisterRus81 = "   3   ";
    private static String SCashRegisterRus82 = "   3   ";
    private static String SCashRegisterRus83 = "    3   ";
    private static String SCashRegisterRus84 = "    3   ";
    private static String SCashRegisterRus85 = "   4   ";
    private static String SCashRegisterRus86 = "   4   ";
    private static String SCashRegisterRus87 = "    4   ";
    private static String SCashRegisterRus88 = "    4   ";
    private static String SCashRegisterRus89 = "   5   ";
    private static String SCashRegisterRus8A = "   5   ";
    private static String SCashRegisterRus8B = "    5   ";
    private static String SCashRegisterRus8C = "    5   ";
    private static String SCashRegisterRus8D = "   6   ";
    private static String SCashRegisterRus8E = "   6   ";
    private static String SCashRegisterRus8F = "    6   ";
    private static String SCashRegisterRus90 = "    6   ";
    private static String SCashRegisterRus91 = "   7   ";
    private static String SCashRegisterRus92 = "   7   ";
    private static String SCashRegisterRus93 = "    7   ";
    private static String SCashRegisterRus94 = "    7   ";
    private static String SCashRegisterRus95 = "   8   ";
    private static String SCashRegisterRus96 = "   8   ";
    private static String SCashRegisterRus97 = "    8   ";
    private static String SCashRegisterRus98 = "    8   ";
    private static String SCashRegisterRus99 = "   9   ";
    private static String SCashRegisterRus9A = "   9   ";
    private static String SCashRegisterRus9B = "    9   ";
    private static String SCashRegisterRus9C = "    9   ";
    private static String SCashRegisterRus9D = "   10   ";
    private static String SCashRegisterRus9E = "   10   ";
    private static String SCashRegisterRus9F = "    10   ";
    private static String SCashRegisterRusA0 = "    10   ";
    private static String SCashRegisterRusA1 = "   11   ";
    private static String SCashRegisterRusA2 = "   11   ";
    private static String SCashRegisterRusA3 = "    11   ";
    private static String SCashRegisterRusA4 = "    11   ";
    private static String SCashRegisterRusA5 = "   12   ";
    private static String SCashRegisterRusA6 = "   12   ";
    private static String SCashRegisterRusA7 = "    12   ";
    private static String SCashRegisterRusA8 = "    12   ";
    private static String SCashRegisterRusA9 = "   13   ";
    private static String SCashRegisterRusAA = "   13   ";
    private static String SCashRegisterRusAB = "    13   ";
    private static String SCashRegisterRusAC = "    13   ";
    private static String SCashRegisterRusAD = "   14   ";
    private static String SCashRegisterRusAE = "   14   ";
    private static String SCashRegisterRusAF = "    14   ";
    private static String SCashRegisterRusB0 = "    14   ";
    private static String SCashRegisterRusB1 = "   15   ";
    private static String SCashRegisterRusB2 = "   15   ";
    private static String SCashRegisterRusB3 = "    15   ";
    private static String SCashRegisterRusB4 = "    15   ";
    private static String SCashRegisterRusB5 = "   16   ";
    private static String SCashRegisterRusB6 = "   16   ";
    private static String SCashRegisterRusB7 = "    16   ";
    private static String SCashRegisterRusB8 = "    16   ";
    private static String SCashRegisterRusB9 = "     ";
    private static String SCashRegisterRusBA = "     ";
    private static String SCashRegisterRusBB = "      ";
    private static String SCashRegisterRusBC = "      ";
    private static String SCashRegisterRusBD = "     ";
    private static String SCashRegisterRusBE = "     ";
    private static String SCashRegisterRusBF = "      ";
    private static String SCashRegisterRusC0 = "      ";
    private static String SCashRegisterRusC1 = "     ";
    private static String SCashRegisterRusC2 = "     ";
    private static String SCashRegisterRusC3 = "      ";
    private static String SCashRegisterRusC4 = "      ";
    private static String SCashRegisterRusC5 = "     2  ";
    private static String SCashRegisterRusC6 = "     2  ";
    private static String SCashRegisterRusC7 = "      2  ";
    private static String SCashRegisterRusC8 = "      2  ";
    private static String SCashRegisterRusC9 = "     3  ";
    private static String SCashRegisterRusCA = "     3  ";
    private static String SCashRegisterRusCB = "      3  ";
    private static String SCashRegisterRusCC = "      3  ";
    private static String SCashRegisterRusCD = "     4  ";
    private static String SCashRegisterRusCE = "     4  ";
    private static String SCashRegisterRusCF = "      4  ";
    private static String SCashRegisterRusD0 = "      4  ";
    private static String SCashRegisterRusD1 = "       ";
    private static String SCashRegisterRusD2 = "       ";
    private static String SCashRegisterRusD3 = "        ";
    private static String SCashRegisterRusD4 = "        ";
    private static String SCashRegisterRusD5 = "       ";
    private static String SCashRegisterRusD6 = "       ";
    private static String SCashRegisterRusD7 = "        ";
    private static String SCashRegisterRusD8 = "        ";
    private static String SCashRegisterRusD9 = "       ";
    private static String SCashRegisterRusDA = "       ";
    private static String SCashRegisterRusDB = "        ";
    private static String SCashRegisterRusDC = "        ";
    private static String SCashRegisterRusDD = "       ";
    private static String SCashRegisterRusDE = "       ";
    private static String SCashRegisterRusDF = "        ";
    private static String SCashRegisterRusE0 = "        ";
    private static String SCashRegisterRusE1 = "       ";
    private static String SCashRegisterRusE2 = "       ";
    private static String SCashRegisterRusE3 = "        ";
    private static String SCashRegisterRusE4 = "        ";
    private static String SCashRegisterRusE5 = "       ";
    private static String SCashRegisterRusE6 = "       ";
    private static String SCashRegisterRusE7 = "        ";
    private static String SCashRegisterRusE8 = "        ";
    private static String SCashRegisterRusE9 = "       ";
    private static String SCashRegisterRusEA = "       ";
    private static String SCashRegisterRusEB = "        ";
    private static String SCashRegisterRusEC = "        ";
    private static String SCashRegisterRusED = "       ";
    private static String SCashRegisterRusEE = "       ";
    private static String SCashRegisterRusEF = "        ";
    private static String SCashRegisterRusF0 = "        ";
    private static String SCashRegisterRusF1 = "   ";
    private static String SCashRegisterRusF2 = "   ";
    private static String SCashRegisterRusF3 = "   ";
    private static String SCashRegisterRusF4 = "   ";
    private static String SCashRegisterRusF5 = "     ";
    private static String SCashRegisterRusF6 = "     ";
    private static String SCashRegisterRusF7 = "      ";
    private static String SCashRegisterRusF8 = "      ";
    private static String SCashRegisterRusF9 = "    ";
    private static String SCashRegisterRusFA = "    ";
    private static String SCashRegisterRusFB = "     ";
    private static String SCashRegisterRusFC = "     ";

    public static String getEngName(int number) {
        switch (number) {
            case 0:
                return "Sales accumulation in 1 department in receipt";
            case 1:
                return "Buys accumulation in 1 department in receipt";
            case 2:
                return "Sales refund accumulation in 1 department in receipt";
            case 3:
                return "Buys refund accumulation in 1 department in receipt";
            case 4:
                return "Sales accumulation in 2 department in receipt";
            case 5:
                return "Buys accumulation in 2 department in receipt";
            case 6:
                return "Sales refund accumulation in 2 department in receipt";
            case 7:
                return "Buys refund accumulation in 2 department in receipt";
            case 8:
                return "Sales accumulation in 3 department in receipt";
            case 9:
                return "Buys accumulation in 3 department in receipt";
            case 10:
                return "Sales refund accumulation in 3 department in receipt";
            case 11:
                return "Buys refund accumulation in 3 department in receipt";
            case 12:
                return "Sales accumulation in 4 department in receipt";
            case 13:
                return "Buys accumulation in 4 department in receipt";
            case 14:
                return "Sales refund accumulation in 4 department in receipt";
            case 15:
                return "Buys refund accumulation in 4 department in receipt";
            case 16:
                return "Sales accumulation in 5 department in receipt";
            case 17:
                return "Buys accumulation in 5 department in receipt";
            case 18:
                return "Sales refund accumulation in 5 department in receipt";
            case 19:
                return "Buys refund accumulation in 5 department in receipt";
            case 20:
                return "Sales accumulation in 6 department in receipt";
            case 21:
                return "Buys accumulation in 6 department in receipt";
            case 22:
                return "Sales refund accumulation in 6 department in receipt";
            case 23:
                return "Buys refund accumulation in 6 department in receipt";
            case 24:
                return "Sales accumulation in 7 department in receipt";
            case 25:
                return "Buys accumulation in 7 department in receipt";
            case 26:
                return "Sales refund accumulation in 7 department in receipt";
            case 27:
                return "Buys refund accumulation in 7 department in receipt";
            case 28:
                return "Sales accumulation in 8 department in receipt";
            case 29:
                return "Buys accumulation in 8 department in receipt";
            case 30:
                return "Sales refund accumulation in 8 department in receipt";
            case 31:
                return "Buys refund accumulation in 8 department in receipt";
            case 32:
                return "Sales accumulation in 9 department in receipt";
            case 33:
                return "Buys accumulation in 9 department in receipt";
            case 34:
                return "Sales refund accumulation in 9 department in receipt";
            case 35:
                return "Buys refund accumulation in 9 department in receipt";
            case 36:
                return "Sales accumulation in 10 department in receipt";
            case 37:
                return "Buys accumulation in 10 department in receipt";
            case 38:
                return "Sales refund accumulation in 10 department in receipt";
            case 39:
                return "Buys refund accumulation in 10 department in receipt";
            case 40:
                return "Sales accumulation in 11 department in receipt";
            case 41:
                return "Buys accumulation in 11 department in receipt";
            case 42:
                return "Sales refund accumulation in 11 department in receipt";
            case 43:
                return "Buys refund accumulation in 11 department in receipt";
            case 44:
                return "Sales accumulation in 12 department in receipt";
            case 45:
                return "Buys accumulation in 12 department in receipt";
            case 46:
                return "Sales refund accumulation in 12 department in receipt";
            case 47:
                return "Buys refund accumulation in 12 department in receipt";
            case 48:
                return "Sales accumulation in 13 department in receipt";
            case 49:
                return "Buys accumulation in 13 department in receipt";
            case 50:
                return "Sales refund accumulation in 13 department in receipt";
            case 51:
                return "Buys refund accumulation in 13 department in receipt";
            case 52:
                return "Sales accumulation in 14 department in receipt";
            case 53:
                return "Buys accumulation in 14 department in receipt";
            case 54:
                return "Sales refund accumulation in 14 department in receipt";
            case 55:
                return "Buys refund accumulation in 14 department in receipt";
            case 56:
                return "Sales accumulation in 15 department in receipt";
            case 57:
                return "Buys accumulation in 15 department in receipt";
            case 58:
                return "Sales refund accumulation in 15 department in receipt";
            case 59:
                return "Buys refund accumulation in 15 department in receipt";
            case 60:
                return "Sales accumulation in 16 department in receipt";
            case 61:
                return "Buys accumulation in 16 department in receipt";
            case 62:
                return "Sales refund accumulation in 16 department in receipt";
            case 63:
                return "Buys refund accumulation in 16 department in receipt";
            case 64:
                return "Discounts accumulation from sales in receipt";
            case 65:
                return "Discounts accumulation from buys in receipt";
            case 66:
                return "Discounts accumulation from sale refunds in receipt";
            case 67:
                return "Discounts accumulation from buy refunds in receipt";
            case 68:
                return "Charges accumulation on sales in receipt";
            case 69:
                return "Charges accumulation on buys in receipt";
            case 70:
                return "Charges accumulation on sale refunds in receipt";
            case 71:
                return "Charges accumulation on buy refunds in receipt";
            case 72:
                return "Cash payment accumulation of sales in receipt";
            case 73:
                return "Cash payment accumulation of buys in receipt";
            case 74:
                return "Cash payment accumulation of sale refunds in receipt";
            case 75:
                return "Cash payment accumulation of buy refunds in receipt";
            case 76:
                return "Payment type 2 accumulation of sales in receipt";
            case 77:
                return "Payment type 2 accumulation of buys in receipt";
            case 78:
                return "Payment type 2 accumulation of sale refunds in receipt";
            case 79:
                return "Payment type 2 accumulation of buy refunds in receipt";
            case 80:
                return "Payment type 3 accumulation of sales in receipt";
            case 81:
                return "Payment type 3 accumulation of buys in receipt";
            case 82:
                return "Payment type 3 accumulation of sale refunds in receipt";
            case 83:
                return "Payment type 3 accumulation of buy refunds in receipt";
            case 84:
                return "Payment type 4 accumulation of sales in receipt";
            case 85:
                return "Payment type 4 accumulation of buys in receipt";
            case 86:
                return "Payment type 4 accumulation of sale refunds in receipt";
            case 87:
                return "Payment type 4 accumulation of buy refunds in receipt";
            case 88:
                return "Tax A turnover of sales in receipt";
            case 89:
                return "Tax A turnover of buys in receipt";
            case 90:
                return "Tax A turnover of sale refunds in receipt";
            case 91:
                return "Tax A turnover of buy refunds in receipt";
            case 92:
                return "Tax B turnover of sales in receipt";
            case 93:
                return "Tax B turnover of buys in receipt";
            case 94:
                return "Tax B turnover of sale refunds in receipt";
            case 95:
                return "Tax B turnover of buy refunds in receipt";
            case 96:
                return "Tax C turnover of sales in receipt";
            case 97:
                return "Tax C turnover of buys in receipt";
            case 98:
                return "Tax C turnover of sale refunds in receipt";
            case 99:
                return "Tax C turnover of buy refunds in receipt";
            case 100:
                return "Tax D turnover of sales in receipt";
            case 101:
                return "Tax D turnover of buys in receipt";
            case 102:
                return "Tax D turnover of sale refunds in receipt";
            case 103:
                return "Tax D turnover of buy refunds in receipt in receipt";
            case 104:
                return "Tax A accumulations of sales in receipt";
            case 105:
                return "Tax A accumulations of buys in receipt";
            case 106:
                return "Tax A accumulations of sale refunds in receipt";
            case 107:
                return "Tax A accumulations of buy refunds in receipt";
            case 108:
                return "Tax B accumulations of sales in receipt";
            case 109:
                return "Tax B accumulations of buys in receipt";
            case 110:
                return "Tax B accumulations of sale refunds in receipt";
            case 111:
                return "Tax B accumulations of buy refunds in receipt";
            case 112:
                return "Tax C accumulations of sales in receipt";
            case 113:
                return "Tax C accumulations of buys in receipt";
            case 114:
                return "Tax C accumulations of sale refunds in receipt";
            case 115:
                return "Tax C accumulations of buy refunds in receipt";
            case 116:
                return "Tax D accumulations of sales in receipt";
            case 117:
                return "Tax D accumulations of buys in receipt";
            case 118:
                return "Tax D accumulations of sale refunds in receipt";
            case 119:
                return "Tax D accumulations of buy refunds in receipt";
            case 120:
                return "Cash total in ECR at receipt closing moment";
            case 121:
                return "Sales accumulation in 1 department in shift";
            case 122:
                return "Buys accumulation in 1 department in shift";
            case 123:
                return "Sales refund accumulation in 1 department in shift";
            case 124:
                return "Buys refund accumulation in 1 department in shift";
            case 125:
                return "Sales accumulation in 2 department in shift";
            case 126:
                return "Buys accumulation in 2 department in shift";
            case 127:
                return "Sales refund accumulation in 2 department in shift";
            case 128:
                return "Buys refund accumulation in 2 department in shift";
            case 129:
                return "Sales accumulation in 3 department in shift";
            case 130:
                return "Buys accumulation in 3 department in shift";
            case 131:
                return "Sales refund accumulation in 3 department in shift";
            case 132:
                return "Buys refund accumulation in 3 department in shift";
            case 133:
                return "Sales accumulation in 4 department in shift";
            case 134:
                return "Buys accumulation in 4 department in shift";
            case 135:
                return "Sales refund accumulation in 4 department in shift";
            case 136:
                return "Buys refund accumulation in 4 department in shift";
            case 137:
                return "Sales accumulation in 5 department in shift";
            case 138:
                return "Buys accumulation in 5 department in shift";
            case 139:
                return "Sales refund accumulation in 5 department in shift";
            case 140:
                return "Buys refund accumulation in 5 department in shift";
            case 141:
                return "Sales accumulation in 6 department in shift";
            case 142:
                return "Buys accumulation in 6 department in shift";
            case 143:
                return "Sales refund accumulation in 6 department in shift";
            case 144:
                return "Buys refund accumulation in 6 department in shift";
            case 145:
                return "Sales accumulation in 7 department in shift";
            case 146:
                return "Buys accumulation in 7 department in shift";
            case 147:
                return "Sales refund accumulation in 7 department in shift";
            case 148:
                return "Buys refund accumulation in 7 department in shift";
            case 149:
                return "Sales accumulation in 8 department in shift";
            case 150:
                return "Buys accumulation in 8 department in shift";
            case 151:
                return "Sales refund accumulation in 8 department in shift";
            case 152:
                return "Buys refund accumulation in 8 department in shift";
            case 153:
                return "Sales accumulation in 9 department in shift";
            case 154:
                return "Buys accumulation in 9 department in shift";
            case 155:
                return "Sales refund accumulation in 9 department in shift";
            case 156:
                return "Buys refund accumulation in 9 department in shift";
            case 157:
                return "Sales accumulation in 10 department in shift";
            case 158:
                return "Buys accumulation in 10 department in shift";
            case 159:
                return "Sales refund accumulation in 10 department in shift";
            case 160:
                return "Buys refund accumulation in 10 department in shift";
            case 161:
                return "Sales accumulation in 11 department in shift";
            case 162:
                return "Buys accumulation in 11 department in shift";
            case 163:
                return "Sales refund accumulation in 11 department in shift";
            case 164:
                return "Buys refund accumulation in 11 department in shift";
            case 165:
                return "Sales accumulation in 12 department in shift";
            case 166:
                return "Buys accumulation in 12 department in shift";
            case 167:
                return "Sales refund accumulation in 12 department in shift";
            case 168:
                return "Buys refund accumulation in 12 department in shift";
            case 169:
                return "Sales accumulation in 13 department in receipt";
            case 170:
                return "Buys accumulation in 13 department in receipt";
            case 171:
                return "Sales refund accumulation in 13 department in receipt";
            case 172:
                return "Buys refund accumulation in 13 department in receipt";
            case 173:
                return "Sales accumulation in 14 department in shift";
            case 174:
                return "Buys accumulation in 14 department in shift";
            case 175:
                return "Sales refund accumulation in 14 department in shift";
            case 176:
                return "Buys refund accumulation in 14 department in shift";
            case 177:
                return "Sales accumulation in 15 department in shift";
            case 178:
                return "Buys accumulation in 15 department in shift";
            case 179:
                return "Sales refund accumulation in 15 department in shift";
            case 180:
                return "Buys refund accumulation in 15 department in shift";
            case 181:
                return "Sales accumulation in 16 department in shift";
            case 182:
                return "Buys accumulation in 16 department in shift";
            case 183:
                return "Sales refund accumulation in 16 department in shift";
            case 184:
                return "Buys refund accumulation in 16 department in shift";
            case 185:
                return "Discounts accumulation on sales in shift";
            case 186:
                return "Discounts accumulation on buys in shift";
            case 187:
                return "Discounts accumulation on sale refunds in shift";
            case 188:
                return "Discounts accumulation on buy refunds in shift";
            case 189:
                return "Charges accumulation on sales in shift";
            case 190:
                return "Charges accumulation on buys in shift";
            case 191:
                return "Charges accumulation on sale refunds in shift";
            case 192:
                return "Charges accumulation on buy refunds in shift";
            case 193:
                return "Cash payment accumulation of sales in shift";
            case 194:
                return "Cash payment accumulation of buys in shift";
            case 195:
                return "Cash payment accumulation of sale refunds shift";
            case 196:
                return "Cash payment accumulation of buy refunds in shift";
            case 197:
                return "Payment type 2 accumulation of sales in shift";
            case 198:
                return "Payment type 2 accumulation of buys in shift";
            case 199:
                return "Payment type 2 accumulation of sale refunds in shift";
            case 200:
                return "Payment type 2 accumulation of buy refunds in shift";
            case 201:
                return "Payment type 3 accumulation of sales in shift";
            case 202:
                return "Payment type 3 accumulation of buys in shift";
            case 203:
                return "Payment type 3 accumulation of sale refunds in shift";
            case 204:
                return "Payment type 3 accumulation of buy refunds in shift";
            case 205:
                return "Payment type 4 accumulation of sales in shift";
            case 206:
                return "Payment type 4 accumulation of buys in shift";
            case 207:
                return "Payment type 4 accumulation of sale refunds in shift";
            case 208:
                return "Payment type 4 accumulation of buy refunds in shift";
            case 209:
                return "Tax A turnover of sales in shift";
            case 210:
                return "Tax A turnover of buys in shift";
            case 211:
                return "Tax A turnover of sale refunds in shift";
            case 212:
                return "Tax A turnover of buy refunds in shift";
            case 213:
                return "Tax B turnover of sales in shift";
            case 214:
                return "Tax B turnover of buys in shift";
            case 215:
                return "Tax B turnover of sale refunds in shift";
            case 216:
                return "Tax B turnover of buy refunds in shift";
            case 217:
                return "Tax C turnover of sales in shift";
            case 218:
                return "Tax C turnover of buys in shift";
            case 219:
                return "Tax C turnover of sale refunds in shift";
            case 220:
                return "Tax C turnover of buy refunds in shift";
            case 221:
                return "Tax D turnover of sales in shift";
            case 222:
                return "Tax D turnover of buys in shift";
            case 223:
                return "Tax D turnover of sale refunds in shift";
            case 224:
                return "Tax D turnover of buy refunds in shift";
            case 225:
                return "Tax A accumulations of sales in shift";
            case 226:
                return "Tax A accumulations of buys in shift";
            case 227:
                return "Tax A accumulations of sale refunds in shift";
            case 228:
                return "Tax A accumulations of buy refunds in shift";
            case 229:
                return "Tax B accumulations of sales in shift";
            case 230:
                return "Tax B accumulations of buys in shift";
            case 231:
                return "Tax B accumulations of sale refunds in shift";
            case 232:
                return "Tax B accumulations of buy refunds in shift";
            case 233:
                return "Tax C accumulations of sales in shift";
            case 234:
                return "Tax C accumulations of buys in shift";
            case 235:
                return "Tax C accumulations of sale refunds in shift";
            case 236:
                return "Tax C accumulations of buy refunds in shift";
            case 237:
                return "Tax D accumulations of sales in shift";
            case 238:
                return "Tax D accumulations of buys in shift";
            case 239:
                return "Tax D accumulations of sale refunds in shift";
            case 240:
                return "Tax D accumulations of buy refunds in shift";
            case 241:
                return "Cash total in ECR accumulation";
            case 242:
                return "Cash in accumulation in shift";
            case 243:
                return "Cash out accumulation in shift";
            case 244:
                return "Non-zeroise sum before fiscalization";
            case 245:
                return "Sales total in shift from EJ";
            case 246:
                return "Buys total in shift from EJ";
            case 247:
                return "Sale refunds total in shift from EJ";
            case 248:
                return "Buy refunds total in shift from EJ";
            case 249:
                return "Daily voided sale receipts total";
            case 250:
                return "Daily voided buy receipts total";
            case 251:
                return "Daily voided sale return receipts total";
            case 252:
                return "Daily voided buy return receipts total";
            default:
                return "Unknown register";
        }
    }

    public static String getRusName(int number) {
        switch (number) {
            case 0x00:
                return SCashRegisterRus00;
            case 0x01:
                return SCashRegisterRus01;
            case 0x02:
                return SCashRegisterRus02;
            case 0x03:
                return SCashRegisterRus03;
            case 0x04:
                return SCashRegisterRus04;
            case 0x05:
                return SCashRegisterRus05;
            case 0x06:
                return SCashRegisterRus06;
            case 0x07:
                return SCashRegisterRus07;
            case 0x08:
                return SCashRegisterRus08;
            case 0x09:
                return SCashRegisterRus09;
            case 0x0A:
                return SCashRegisterRus0A;
            case 0x0B:
                return SCashRegisterRus0B;
            case 0x0C:
                return SCashRegisterRus0C;
            case 0x0D:
                return SCashRegisterRus0D;
            case 0x0E:
                return SCashRegisterRus0E;
            case 0x0F:
                return SCashRegisterRus0F;
            case 0x10:
                return SCashRegisterRus10;
            case 0x11:
                return SCashRegisterRus11;
            case 0x12:
                return SCashRegisterRus12;
            case 0x13:
                return SCashRegisterRus13;
            case 0x14:
                return SCashRegisterRus14;
            case 0x15:
                return SCashRegisterRus15;
            case 0x16:
                return SCashRegisterRus16;
            case 0x17:
                return SCashRegisterRus17;
            case 0x18:
                return SCashRegisterRus18;
            case 0x19:
                return SCashRegisterRus19;
            case 0x1A:
                return SCashRegisterRus1A;
            case 0x1B:
                return SCashRegisterRus1B;
            case 0x1C:
                return SCashRegisterRus1C;
            case 0x1D:
                return SCashRegisterRus1D;
            case 0x1E:
                return SCashRegisterRus1E;
            case 0x1F:
                return SCashRegisterRus1F;
            case 0x20:
                return SCashRegisterRus20;
            case 0x21:
                return SCashRegisterRus21;
            case 0x22:
                return SCashRegisterRus22;
            case 0x23:
                return SCashRegisterRus23;
            case 0x24:
                return SCashRegisterRus24;
            case 0x25:
                return SCashRegisterRus25;
            case 0x26:
                return SCashRegisterRus26;
            case 0x27:
                return SCashRegisterRus27;
            case 0x28:
                return SCashRegisterRus28;
            case 0x29:
                return SCashRegisterRus29;
            case 0x2A:
                return SCashRegisterRus2A;
            case 0x2B:
                return SCashRegisterRus2B;
            case 0x2C:
                return SCashRegisterRus2C;
            case 0x2D:
                return SCashRegisterRus2D;
            case 0x2E:
                return SCashRegisterRus2E;
            case 0x2F:
                return SCashRegisterRus2F;
            case 0x30:
                return SCashRegisterRus30;
            case 0x31:
                return SCashRegisterRus31;
            case 0x32:
                return SCashRegisterRus32;
            case 0x33:
                return SCashRegisterRus33;
            case 0x34:
                return SCashRegisterRus34;
            case 0x35:
                return SCashRegisterRus35;
            case 0x36:
                return SCashRegisterRus36;
            case 0x37:
                return SCashRegisterRus37;
            case 0x38:
                return SCashRegisterRus38;
            case 0x39:
                return SCashRegisterRus39;
            case 0x3A:
                return SCashRegisterRus3A;
            case 0x3B:
                return SCashRegisterRus3B;
            case 0x3C:
                return SCashRegisterRus3C;
            case 0x3D:
                return SCashRegisterRus3D;
            case 0x3E:
                return SCashRegisterRus3E;
            case 0x3F:
                return SCashRegisterRus3F;
            case 0x40:
                return SCashRegisterRus40;
            case 0x41:
                return SCashRegisterRus41;
            case 0x42:
                return SCashRegisterRus42;
            case 0x43:
                return SCashRegisterRus43;
            case 0x44:
                return SCashRegisterRus44;
            case 0x45:
                return SCashRegisterRus45;
            case 0x46:
                return SCashRegisterRus46;
            case 0x47:
                return SCashRegisterRus47;
            case 0x48:
                return SCashRegisterRus48;
            case 0x49:
                return SCashRegisterRus49;
            case 0x4A:
                return SCashRegisterRus4A;
            case 0x4B:
                return SCashRegisterRus4B;
            case 0x4C:
                return SCashRegisterRus4C;
            case 0x4D:
                return SCashRegisterRus4D;
            case 0x4E:
                return SCashRegisterRus4E;
            case 0x4F:
                return SCashRegisterRus4F;
            case 0x50:
                return SCashRegisterRus50;
            case 0x51:
                return SCashRegisterRus51;
            case 0x52:
                return SCashRegisterRus52;
            case 0x53:
                return SCashRegisterRus53;
            case 0x54:
                return SCashRegisterRus54;
            case 0x55:
                return SCashRegisterRus55;
            case 0x56:
                return SCashRegisterRus56;
            case 0x57:
                return SCashRegisterRus57;
            case 0x58:
                return SCashRegisterRus58;
            case 0x59:
                return SCashRegisterRus59;
            case 0x5A:
                return SCashRegisterRus5A;
            case 0x5B:
                return SCashRegisterRus5B;
            case 0x5C:
                return SCashRegisterRus5C;
            case 0x5D:
                return SCashRegisterRus5D;
            case 0x5E:
                return SCashRegisterRus5E;
            case 0x5F:
                return SCashRegisterRus5F;
            case 0x60:
                return SCashRegisterRus60;
            case 0x61:
                return SCashRegisterRus61;
            case 0x62:
                return SCashRegisterRus62;
            case 0x63:
                return SCashRegisterRus63;
            case 0x64:
                return SCashRegisterRus64;
            case 0x65:
                return SCashRegisterRus65;
            case 0x66:
                return SCashRegisterRus66;
            case 0x67:
                return SCashRegisterRus67;
            case 0x68:
                return SCashRegisterRus68;
            case 0x69:
                return SCashRegisterRus69;
            case 0x6A:
                return SCashRegisterRus6A;
            case 0x6B:
                return SCashRegisterRus6B;
            case 0x6C:
                return SCashRegisterRus6C;
            case 0x6D:
                return SCashRegisterRus6D;
            case 0x6E:
                return SCashRegisterRus6E;
            case 0x6F:
                return SCashRegisterRus6F;
            case 0x70:
                return SCashRegisterRus70;
            case 0x71:
                return SCashRegisterRus71;
            case 0x72:
                return SCashRegisterRus72;
            case 0x73:
                return SCashRegisterRus73;
            case 0x74:
                return SCashRegisterRus74;
            case 0x75:
                return SCashRegisterRus75;
            case 0x76:
                return SCashRegisterRus76;
            case 0x77:
                return SCashRegisterRus77;
            case 0x78:
                return SCashRegisterRus78;
            case 0x79:
                return SCashRegisterRus79;
            case 0x7A:
                return SCashRegisterRus7A;
            case 0x7B:
                return SCashRegisterRus7B;
            case 0x7C:
                return SCashRegisterRus7C;
            case 0x7D:
                return SCashRegisterRus7D;
            case 0x7E:
                return SCashRegisterRus7E;
            case 0x7F:
                return SCashRegisterRus7F;
            case 0x80:
                return SCashRegisterRus80;
            case 0x81:
                return SCashRegisterRus81;
            case 0x82:
                return SCashRegisterRus82;
            case 0x83:
                return SCashRegisterRus83;
            case 0x84:
                return SCashRegisterRus84;
            case 0x85:
                return SCashRegisterRus85;
            case 0x86:
                return SCashRegisterRus86;
            case 0x87:
                return SCashRegisterRus87;
            case 0x88:
                return SCashRegisterRus88;
            case 0x89:
                return SCashRegisterRus89;
            case 0x8A:
                return SCashRegisterRus8A;
            case 0x8B:
                return SCashRegisterRus8B;
            case 0x8C:
                return SCashRegisterRus8C;
            case 0x8D:
                return SCashRegisterRus8D;
            case 0x8E:
                return SCashRegisterRus8E;
            case 0x8F:
                return SCashRegisterRus8F;
            case 0x90:
                return SCashRegisterRus90;
            case 0x91:
                return SCashRegisterRus91;
            case 0x92:
                return SCashRegisterRus92;
            case 0x93:
                return SCashRegisterRus93;
            case 0x94:
                return SCashRegisterRus94;
            case 0x95:
                return SCashRegisterRus95;
            case 0x96:
                return SCashRegisterRus96;
            case 0x97:
                return SCashRegisterRus97;
            case 0x98:
                return SCashRegisterRus98;
            case 0x99:
                return SCashRegisterRus99;
            case 0x9A:
                return SCashRegisterRus9A;
            case 0x9B:
                return SCashRegisterRus9B;
            case 0x9C:
                return SCashRegisterRus9C;
            case 0x9D:
                return SCashRegisterRus9D;
            case 0x9E:
                return SCashRegisterRus9E;
            case 0x9F:
                return SCashRegisterRus9F;
            case 0xA0:
                return SCashRegisterRusA0;
            case 0xA1:
                return SCashRegisterRusA1;
            case 0xA2:
                return SCashRegisterRusA2;
            case 0xA3:
                return SCashRegisterRusA3;
            case 0xA4:
                return SCashRegisterRusA4;
            case 0xA5:
                return SCashRegisterRusA5;
            case 0xA6:
                return SCashRegisterRusA6;
            case 0xA7:
                return SCashRegisterRusA7;
            case 0xA8:
                return SCashRegisterRusA8;
            case 0xA9:
                return SCashRegisterRusA9;
            case 0xAA:
                return SCashRegisterRusAA;
            case 0xAB:
                return SCashRegisterRusAB;
            case 0xAC:
                return SCashRegisterRusAC;
            case 0xAD:
                return SCashRegisterRusAD;
            case 0xAE:
                return SCashRegisterRusAE;
            case 0xAF:
                return SCashRegisterRusAF;
            case 0xB0:
                return SCashRegisterRusB0;
            case 0xB1:
                return SCashRegisterRusB1;
            case 0xB2:
                return SCashRegisterRusB2;
            case 0xB3:
                return SCashRegisterRusB3;
            case 0xB4:
                return SCashRegisterRusB4;
            case 0xB5:
                return SCashRegisterRusB5;
            case 0xB6:
                return SCashRegisterRusB6;
            case 0xB7:
                return SCashRegisterRusB7;
            case 0xB8:
                return SCashRegisterRusB8;
            case 0xB9:
                return SCashRegisterRusB9;
            case 0xBA:
                return SCashRegisterRusBA;
            case 0xBB:
                return SCashRegisterRusBB;
            case 0xBC:
                return SCashRegisterRusBC;
            case 0xBD:
                return SCashRegisterRusBD;
            case 0xBE:
                return SCashRegisterRusBE;
            case 0xBF:
                return SCashRegisterRusBF;
            case 0xC0:
                return SCashRegisterRusC0;
            case 0xC1:
                return SCashRegisterRusC1;
            case 0xC2:
                return SCashRegisterRusC2;
            case 0xC3:
                return SCashRegisterRusC3;
            case 0xC4:
                return SCashRegisterRusC4;
            case 0xC5:
                return SCashRegisterRusC5;
            case 0xC6:
                return SCashRegisterRusC6;
            case 0xC7:
                return SCashRegisterRusC7;
            case 0xC8:
                return SCashRegisterRusC8;
            case 0xC9:
                return SCashRegisterRusC9;
            case 0xCA:
                return SCashRegisterRusCA;
            case 0xCB:
                return SCashRegisterRusCB;
            case 0xCC:
                return SCashRegisterRusCC;
            case 0xCD:
                return SCashRegisterRusCD;
            case 0xCE:
                return SCashRegisterRusCE;
            case 0xCF:
                return SCashRegisterRusCF;
            case 0xD0:
                return SCashRegisterRusD0;
            case 0xD1:
                return SCashRegisterRusD1;
            case 0xD2:
                return SCashRegisterRusD2;
            case 0xD3:
                return SCashRegisterRusD3;
            case 0xD4:
                return SCashRegisterRusD4;
            case 0xD5:
                return SCashRegisterRusD5;
            case 0xD6:
                return SCashRegisterRusD6;
            case 0xD7:
                return SCashRegisterRusD7;
            case 0xD8:
                return SCashRegisterRusD8;
            case 0xD9:
                return SCashRegisterRusD9;
            case 0xDA:
                return SCashRegisterRusDA;
            case 0xDB:
                return SCashRegisterRusDB;
            case 0xDC:
                return SCashRegisterRusDC;
            case 0xDD:
                return SCashRegisterRusDD;
            case 0xDE:
                return SCashRegisterRusDE;
            case 0xDF:
                return SCashRegisterRusDF;
            case 0xE0:
                return SCashRegisterRusE0;
            case 0xE1:
                return SCashRegisterRusE1;
            case 0xE2:
                return SCashRegisterRusE2;
            case 0xE3:
                return SCashRegisterRusE3;
            case 0xE4:
                return SCashRegisterRusE4;
            case 0xE5:
                return SCashRegisterRusE5;
            case 0xE6:
                return SCashRegisterRusE6;
            case 0xE7:
                return SCashRegisterRusE7;
            case 0xE8:
                return SCashRegisterRusE8;
            case 0xE9:
                return SCashRegisterRusE9;
            case 0xEA:
                return SCashRegisterRusEA;
            case 0xEB:
                return SCashRegisterRusEB;
            case 0xEC:
                return SCashRegisterRusEC;
            case 0xED:
                return SCashRegisterRusED;
            case 0xEE:
                return SCashRegisterRusEE;
            case 0xEF:
                return SCashRegisterRusEF;
            case 0xF0:
                return SCashRegisterRusF0;
            case 0xF1:
                return SCashRegisterRusF1;
            case 0xF2:
                return SCashRegisterRusF2;
            case 0xF3:
                return SCashRegisterRusF3;
            case 0xF4:
                return SCashRegisterRusF4;
            case 0xF5:
                return SCashRegisterRusF5;
            case 0xF6:
                return SCashRegisterRusF6;
            case 0xF7:
                return SCashRegisterRusF7;
            case 0xF8:
                return SCashRegisterRusF8;
            case 0xF9:
                return SCashRegisterRusF9;
            case 0xFA:
                return SCashRegisterRusFA;
            case 0xFB:
                return SCashRegisterRusFB;
            case 0xFC:
                return SCashRegisterRusFC;
            default:
                return "Unknown register";
        }
    }
}