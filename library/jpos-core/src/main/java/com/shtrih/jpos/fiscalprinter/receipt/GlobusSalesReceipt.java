/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.jpos.fiscalprinter.receipt;

import java.util.Vector;

import jpos.JposConst;
import jpos.JposException;
import jpos.FiscalPrinterConst;

import org.apache.log4j.Logger;

import com.shtrih.util.Localizer;
import com.shtrih.util.MathUtils;
import com.shtrih.util.StringUtils;
import com.shtrih.fiscalprinter.FontNumber;
import com.shtrih.jpos.fiscalprinter.PayType;
import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.jpos.fiscalprinter.SmFptrConst;
import com.shtrih.fiscalprinter.command.PriceItem;
import com.shtrih.fiscalprinter.model.PrinterModel;
import com.shtrih.fiscalprinter.command.AmountItem;
import com.shtrih.fiscalprinter.command.PrinterConst;
import com.shtrih.fiscalprinter.receipt.PrinterReceipt;
import com.shtrih.fiscalprinter.command.CloseRecParams;

/**
 * @author V.Kravtsov
 */
public class GlobusSalesReceipt extends CustomReceipt implements FiscalReceipt {
    // /////////////////////////////////////////////////////////////////////////
    // text constants

    public class TextItem {

        private final String text;
        private final int font;
        private final int station;

        public TextItem(String text, int font, int station) {
            this.text = text;
            this.font = font;
            this.station = station;
        }

        public String getText() {
            return text;
        }

        public int getFont() {
            return font;
        }

        public int getStation() {
            return station;
        }
    }

    private static final String PrinterChargeText = "????????";
    private static final String PrinterDiscountText = "??????";
    private static final String PrinterVoidChargeText = "?????? ????????";
    private static final String PrinterVoidDiscountText = "?????? ??????";
    private static final String PrinterItemVoidText = "??????";
    private final Vector preLines = new Vector();
    private static Logger logger = Logger.getLogger(GlobusSalesReceipt.class);
    private int receiptType = 0;

    public GlobusSalesReceipt(ReceiptContext context, int receiptType) {
        super(context);
        this.receiptType = receiptType;
    }

    
    public boolean isOpened() {
        return getReceipt().isOpened();
    }

    public void openReceipt(int receiptType) throws Exception {
        if (!getReceipt().isOpened()) {
            getPrinter().openReceipt(receiptType);
            getReceipt().openReceipt(receiptType);
        }
    }

    
    public void beginFiscalReceipt(boolean printHeader) throws Exception {
        getReceipt().reset();
    }

    
    public void printRecItem(String description, long price, int quantity,
            int vatInfo, long unitPrice, String unitName) throws Exception {

        doOpenReceipt();
        printPreLine();
        // if unitPrice is zero then we use price and quantity = 1000
        if (unitPrice == 0) {
            quantity = 1000;
        } else {
            if (quantity == 0) {
                quantity = 1000;
            }
            price = unitPrice;
        }
        description = getPrinter().printDescription(description);
        printReceiptItem(description, price, quantity, vatInfo);
        getPrinter().printPostLine();
    }

    public String getVatText(int vatInfo) {
        if (getParams().RFShowTaxLetters) {
            switch (vatInfo) {
                case 1:
                    return "_?";
                case 2:
                    return "_?";
                case 3:
                    return "_?";
                case 4:
                    return "_?";
                default:
                    return "";
            }
        }
        return "";
    }

    public void printLines(String line1, String line2) throws Exception {
        String text = formatLines(line1, line2);
        getPrinter().printText(text);
    }

    public String formatLines(String line1, String line2) throws Exception {
        return alignLines(line1, line2, getPrinter().getTextLength());
    }

    public static String alignLines(String line1, String line2, int len) {
        int lastIndex = MathUtils.min(len, line2.length());
        String result = line2.substring(0, lastIndex);
        if (result.length() < len) {
            lastIndex = MathUtils.min(line1.length(), len - result.length());
            String S = line1.substring(0, lastIndex);
            result = S
                    + StringUtils.stringOfChar(' ',
                            len - result.length() - S.length()) + result;
        }
        return result;
    }

    public String quantityToStr(long value) throws Exception {
        if ((value % 1000) == 0) {
            return String.valueOf(value / 1000);
        } else {
            return StringUtils.quantityToString(value / 1000.0);
        }
    }

    public void printReceiptItem(String description, long price, long quantity,
            int vatInfo) throws Exception {
        price = Math.abs(price);
        quantity = Math.abs(quantity);

        PriceItem item = new PriceItem();
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setDepartment(context.getParams().department);
        item.setTax1(vatInfo);
        item.setTax2(0);
        item.setTax3(0);
        item.setTax4(0);
        item.setText(description);
        getReceipt().printSale(item);

        String quantityText = " " + quantityToStr(quantity) + " x "
                + StringUtils.amountToString(price);
        String amountText = StringUtils.amountToString(item.getAmount());

        logger.debug("quantityText = " + quantityText);
        String text = " "
                + StringUtils.appendLeft(quantityText,
                        getParams().RFQuantityLength)
                + " = "
                + StringUtils
                        .appendLeft(amountText, getParams().RFAmountLength)
                + getVatText(vatInfo);

        text = formatLines(description, text);
        getPrinter().printText(text);
    }

    
    public PrinterReceipt getReceipt() {
        return context.getReceipt();
    }

    
    public void endFiscalReceipt(boolean printHeader) throws Exception {
        if (getReceipt().isCancelled()) {
            return;
        }

        printReceiptItems();
        printSeparator();

        CloseRecParams closeParams = new CloseRecParams();

        closeParams.setSum1(getReceipt().getPayment(0));
        closeParams.setSum2(getReceipt().getPayment(1));
        closeParams.setSum3(getReceipt().getPayment(2));
        closeParams.setSum4(getReceipt().getPayment(3));
        closeParams.setDiscount(0);
        closeParams.setTax1(0);
        closeParams.setTax2(0);
        closeParams.setTax3(0);
        closeParams.setTax4(0);
        closeParams.setText(getParams().closeReceiptText);
        getFPrinter().closeReceipt(closeParams);
    }

    private void printSeparator() throws Exception {
        if (getParams().RFSeparatorLine == SmFptrConst.SMFPTR_SEPARATOR_LINE_DASHES) {
            String text = StringUtils.stringOfChar('-', getPrinter()
                    .getTextLength());
            getPrinter().printText(text);
        }
        if (getParams().RFSeparatorLine == SmFptrConst.SMFPTR_SEPARATOR_LINE_GRAPHICS) {
            getPrinter().printSeparator(SmFptrConst.SMFPTR_SEPARATOR_WHITE, 5);
            getPrinter().printSeparator(SmFptrConst.SMFPTR_SEPARATOR_BLACK, 3);
            getPrinter().printSeparator(SmFptrConst.SMFPTR_SEPARATOR_WHITE, 5);
        }
    }

    
    public void printRecRefund(String description, long amount, int vatInfo)
            throws Exception {
        checkAmount(amount);
        checkVatInfo(vatInfo);
        updateRecType();
        doOpenReceipt();
        printPreLine();
        printReceiptItem(description, amount, 1000, vatInfo);
        getPrinter().printPostLine();
    }

    
    public void printRecTotal(long total, long payment, long payType,
            String description) throws Exception {
        checkAmount(total);
        checkAmount(payment);

        PayType paymentType = getPayType(description);
        if (paymentType.isCashless() && ((payment + getPayment()) > total)) {
            payment = total - getPayment();
        }
        getReceipt().addPayment(payment, paymentType.getValue());
        getPrinter().printPostLine();
    }

    
    public void printRecItemVoid(String description, long price, int quantity,
            int vatInfo, long unitPrice, String unitName) throws Exception {
        doOpenReceipt();
        // checkPrice(price);
        // checkQuantity(quantity);
        // checkPrice(unitPrice);
        checkVatInfo(vatInfo);

        long itemPrice;
        long itemQuantity;
        if (unitPrice == 0) {
            itemPrice = price;
            itemQuantity = 1000;
        } else {
            if (quantity == 0) {
                quantity = 1000;
            }
            itemQuantity = quantity / 1000;
            itemPrice = unitPrice;
        }
        printReceiptItem(description, itemPrice, -itemQuantity, vatInfo);
    }

    
    public void printRecSubtotal(long amount) throws Exception {
        doOpenReceipt();
        checkAmount(amount);
        printPreLine();
        getPrinter().printStrings(getParams().subtotalText,
                "=" + StringUtils.amountToString(getReceipt().getTotal()));
        getPrinter().printPostLine();
    }

    public void checkVatInfo(int vatInfo) throws Exception {
        if ((vatInfo < 0) || (vatInfo > 4)) {
            throw new JposException(JposConst.JPOS_E_EXTENDED,
                    FiscalPrinterConst.JPOS_EFPTR_BAD_VAT,
                    "Invalid VatInfo value");
        }
    }

    
    public void printRecItemAdjustment(int adjustmentType, String description,
            long amount, int vatInfo) throws Exception {
        doOpenReceipt();
        printPreLine();
        checkAdjAmount(adjustmentType, amount);
        checkVatInfo(vatInfo);

        switch (adjustmentType) {
            case FiscalPrinterConst.FPTR_AT_AMOUNT_DISCOUNT:
                printDiscount(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_AMOUNT_SURCHARGE:
                printCharge(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_DISCOUNT:
                amount = getReceipt().getItemPercentAdjustmentAmount(amount);
                printDiscount(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_SURCHARGE:
                amount = getReceipt().getItemPercentAdjustmentAmount(amount);
                printCharge(description, amount, vatInfo);
                break;

            default:
                throw new JposException(JposConst.JPOS_E_ILLEGAL,
                        Localizer.getString(Localizer.invalidParameterValue)
                                + "adjustmentType");
        }
    }

    
    public void printRecSubtotalAdjustment(int adjustmentType,
            String description, long amount) throws Exception {
        printPreLine();
        checkAdjAmount(adjustmentType, amount);
        recSubtotalAdjustment(description, adjustmentType, amount);
        getPrinter().printPostLine();
    }

    
    public void printRecVoidItem(String description, long amount, int quantity,
            int adjustmentType, long adjustment, int vatInfo) throws Exception {
        doOpenReceipt();
        checkAmount(amount);
        checkVatInfo(vatInfo);
        if (quantity <= 0) {
            quantity = 1000;
        }
        printReceiptItem(description, amount, -quantity, vatInfo);
    }

    
    public void printRecRefundVoid(String description, long amount, int vatInfo)
            throws Exception {
        doOpenReceipt();
        checkAmount(amount);
        checkVatInfo(vatInfo);
        printReceiptItem(description, amount, -1000, vatInfo);
    }

    
    public void printRecPackageAdjustment(int adjustmentType,
            String description, String vatAdjustment) throws Exception {
        doOpenReceipt();
        printLines(description, vatAdjustment);
    }

    
    public void printRecPackageAdjustVoid(int adjustmentType,
            String vatAdjustment) throws Exception {
        doOpenReceipt();
        printPreLine();
        getPrinter().printText(vatAdjustment);
    }

    
    public void printRecSubtotalAdjustVoid(int adjustmentType, long amount)
            throws Exception {
        checkAdjAmount(adjustmentType, amount);
        recSubtotalAdjustment("", adjustmentType, -amount);
    }

    public void recSubtotalAdjustment(String description, int adjustmentType,
            long amount) throws Exception {
        long summ;
        switch (adjustmentType) {
            case FiscalPrinterConst.FPTR_AT_AMOUNT_DISCOUNT:
                subtotalDiscount(description, amount);
                break;

            case FiscalPrinterConst.FPTR_AT_AMOUNT_SURCHARGE:
                subtotalCharge(description, amount);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_DISCOUNT:
                summ = roundAmount(getReceipt().getTotal() * amount / 100);
                subtotalDiscount(description, summ);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_SURCHARGE:
                summ = roundAmount(getReceipt().getTotal() * amount / 100);
                subtotalCharge(description, summ);
                break;
            default:
                throw new JposException(JposConst.JPOS_E_ILLEGAL,
                        "Invalid AdjustmentType parameter value");
        }
    }

    
    public void printRecItemAdjustmentVoid(int adjustmentType,
            String description, long amount, int vatInfo) throws Exception {
        doOpenReceipt();
        printPreLine();
        checkAdjAmount(adjustmentType, amount);
        checkVatInfo(vatInfo);

        switch (adjustmentType) {
            case FiscalPrinterConst.FPTR_AT_AMOUNT_DISCOUNT:
                printDiscount(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_AMOUNT_SURCHARGE:
                printCharge(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_DISCOUNT:
                amount = getReceipt().getItemPercentAdjustmentAmount(amount);
                printDiscount(description, amount, vatInfo);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_SURCHARGE:
                amount = getReceipt().getItemPercentAdjustmentAmount(amount);
                printCharge(description, amount, vatInfo);
                break;

            default:
                throw new JposException(JposConst.JPOS_E_ILLEGAL,
                        Localizer.getString(Localizer.invalidParameterValue)
                                + "adjustmentType");
        }
    }

    
    public void printRecItemRefund(String description, long amount,
            int quantity, int vatInfo, long unitAmount, String unitName)
            throws Exception {
        checkAmount(amount);
        checkAmount(unitAmount);
        // checkQuantity(quantity);
        checkVatInfo(vatInfo);

        long itemQuantity;
        long itemPrice;
        if ((unitAmount == 0) || (quantity == 0)) {
            itemQuantity = 1000;
            itemPrice = amount;
        } else {
            if (quantity == 0) {
                quantity = 1000;
            }
            itemQuantity = quantity;
            itemPrice = unitAmount;
        }
        updateRecType();
        doOpenReceipt();
        printPreLine();
        printReceiptItem(description, itemPrice, itemQuantity, vatInfo);
    }

    
    public void printRecItemRefundVoid(String description, long amount,
            int quantity, int vatInfo, long unitAmount, String unitName)
            throws Exception {
        checkAmount(amount);
        checkAmount(unitAmount);
        // checkQuantity(quantity);
        checkVatInfo(vatInfo);

        long itemQuantity;
        long itemPrice;
        if ((unitAmount == 0) || (quantity == 0)) {
            itemQuantity = 1000;
            itemPrice = amount;
        } else {
            if (quantity == 0) {
                quantity = 1000;
            }
            itemQuantity = quantity;
            itemPrice = unitAmount;
        }
        updateRecType();
        doOpenReceipt();
        printPreLine();
        printReceiptItem(description, itemPrice, -itemQuantity, vatInfo);
    }

    
    public void printRecVoid(String description) throws Exception {
        getPrinter().printText(description);
        getReceipt().cancel();
    }

    
    public boolean isPayed() {
        return getReceipt().isPayed();
    }

    public void printStorno(long price, int quantity, int department,
            int vatInfo, String description) throws Exception {
        PriceItem item = new PriceItem();
        item.setPrice(price);
        item.setQuantity(quantity);
        item.setDepartment(department);
        item.setTax1(vatInfo);
        item.setTax2(0);
        item.setTax3(0);
        item.setTax4(0);
        item.setText(description);
        getReceipt().printStorno(item);
    }

    public void printDiscount(String description, long amount, int vatInfo)
            throws Exception {
        logger.debug("printDiscount");

        AmountItem item = new AmountItem();
        item.setAmount(amount);
        item.setTax1(vatInfo);
        item.setTax2(0);
        item.setTax3(0);
        item.setTax4(0);
        item.setText(description);
        getReceipt().printDiscount(item);

        String text = "= " + StringUtils.amountToString(amount)
                + getVatText(vatInfo);
        text = formatLines(PrinterDiscountText + " " + description, text);
        getPrinter().printText(text);
    }

    public void printCharge(String description, long amount, int vatInfo)
            throws Exception {
        logger.debug("printCharge");
        AmountItem item = new AmountItem();
        item.setAmount(amount);
        item.setTax1(vatInfo);
        item.setTax2(0);
        item.setTax3(0);
        item.setTax4(0);
        item.setText(description);
        getReceipt().printCharge(item);

        String text = "= " + StringUtils.amountToString(amount)
                + getVatText(vatInfo);
        text = formatLines(PrinterChargeText + " " + description, text);
        getPrinter().printText(text);
    }

    private void checkPercents(long amount) throws Exception {
        if ((amount < 0) || (amount > 10000)) {
            throw new JposException(JposConst.JPOS_E_EXTENDED,
                    FiscalPrinterConst.JPOS_EFPTR_BAD_ITEM_AMOUNT,
                    "Invalid percents value");
        }
    }

    public void checkAdjustment(int adjustmentType, long amount)
            throws Exception {
        switch (adjustmentType) {
            case FiscalPrinterConst.FPTR_AT_AMOUNT_DISCOUNT:
            case FiscalPrinterConst.FPTR_AT_AMOUNT_SURCHARGE:
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_DISCOUNT:
            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_SURCHARGE:
                checkPercents(amount);
                break;

            default:
                throw new JposException(JposConst.JPOS_E_ILLEGAL,
                        Localizer.getString(Localizer.invalidParameterValue)
                                + "adjustmentType");
        }
    }

    public void printReceiptItems() throws Exception {
        if (getReceipt().getTotal() == 0) {
            PriceItem item = new PriceItem();
            item.setPrice(0);
            item.setQuantity(1000);
            item.setDepartment(getParams().department);
            item.setTax1(0);
            item.setTax2(0);
            item.setTax3(0);
            item.setTax4(0);
            item.setText("");
            printItem(item);
        } else {
            for (int i = 0; i < 4; i++) {
                getReceipt().vatAmount[i] = getReceipt().vatAmount[i]
                        - getReceipt().getDiscountAmount(i)
                        + getReceipt().chargeAmount[i];
            }
            for (int i = 0; i < 4; i++) {
                if (getReceipt().vatAmount[i] > 0) {
                    PriceItem item = new PriceItem();
                    item.setPrice(getReceipt().vatAmount[i]);
                    item.setQuantity(1000);
                    item.setDepartment(getParams().department);
                    item.setTax1(i);
                    item.setTax2(0);
                    item.setTax3(0);
                    item.setTax4(0);
                    item.setText("");
                    printItem(item);
                }
            }
            for (int i = 0; i < 4; i++) {
                if (getReceipt().vatAmount[i] < 0) {
                    AmountItem item = new AmountItem();
                    item.setAmount(getReceipt().vatAmount[i]);
                    item.setTax1(i);
                    item.setTax2(0);
                    item.setTax3(0);
                    item.setTax4(0);
                    item.setText("");
                    getFPrinter().printDiscount(item);
                }
            }
        }
    }

    public void printItem(PriceItem item) throws Exception {
        switch (receiptType) {
            case PrinterConst.SMFP_RECTYPE_SALE:
                getFPrinter().printSale(item);
                break;

            case PrinterConst.SMFP_RECTYPE_BUY:
                getFPrinter().printRefund(item);
                break;

            case PrinterConst.SMFP_RECTYPE_RETSALE:
                getFPrinter().printVoidSale(item);
                break;

            case PrinterConst.SMFP_RECTYPE_RETBUY:
                getFPrinter().printVoidRefund(item);
                break;
        }
    }

    public void updateRecType() {
        if ((!getReceipt().hasItems())
                && (receiptType == PrinterConst.SMFP_RECTYPE_SALE)) {
            receiptType = PrinterConst.SMFP_RECTYPE_RETSALE;
        }
    }

    public void doOpenReceipt() throws Exception {
        if (!getReceipt().isOpened()) {
            openReceipt(receiptType);
            printSeparator();
            for (int i = 0; i < preLines.size(); i++) {
                TextItem item = (TextItem) preLines.get(i);

                getFPrinter().printText(item.getStation(), item.getText(),
                        new FontNumber(item.getFont()));
            }
            preLines.clear();
        }
    }

    public static void checkAmount(long amount) throws Exception {
        if (amount < 0) {
            throw new JposException(JposConst.JPOS_E_EXTENDED,
                    FiscalPrinterConst.JPOS_EFPTR_BAD_ITEM_AMOUNT,
                    "Negative amount");
        }
    }

    public void checkAdjAmount(int adjustmentType, long amount)
            throws Exception {
        switch (adjustmentType) {
            case FiscalPrinterConst.FPTR_AT_AMOUNT_DISCOUNT:
            case FiscalPrinterConst.FPTR_AT_AMOUNT_SURCHARGE:
                checkAmount(amount);
                break;

            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_DISCOUNT:
            case FiscalPrinterConst.FPTR_AT_PERCENTAGE_SURCHARGE:
                checkPercents(amount);
                break;

            default:
                throw new JposException(JposConst.JPOS_E_ILLEGAL,
                        "Invalid AdjustmentType parameter value");
        }
    }

    public void subtotalDiscount(String description, long amount)
            throws Exception {

        String text = "= " + StringUtils.amountToString(amount);
        printLines(PrinterDiscountText + ' ' + description, text);
        getReceipt().subtotalDiscount(amount);
    }

    public void subtotalCharge(String description, long amount)
            throws Exception {
        String text = "= " + StringUtils.amountToString(amount);
        printLines(PrinterChargeText + ' ' + description, text);
        getReceipt().subtotalCharge(amount);
    }

    public PayType getPayType(String description) throws Exception {
        int payCode = 0;
        PayType payType = getParams().getPayTypes().get(description);
        if (payType == null) {
            payType = new PayType(0);
        }
        return payType;
    }

    public long getPayment() {
        return getReceipt().getPaymentAmount();
    }

    public void printText(String text, int station, int font) throws Exception {
        if (!getReceipt().isOpened()) {
            TextItem item = new TextItem(text, font, station);
            preLines.add(item);
        } else {
            getFPrinter().printText(station, text, new FontNumber(3));
        }
    }

    
    public void printNormal(int station, String data) throws Exception {
        printText(data, station, 3);
    }

    long roundAmount(double amount) {
        return (long) (amount + 0.5);
    }

    private SMFiscalPrinter getFPrinter() throws Exception {
        return getPrinter().getPrinter();
    }

    public void printPreLine() throws Exception {
        String preLine = getParams().preLine;
        if (preLine.length() > 0) 
        {
            int font = getModel().getDeviceFontSmall();
            getPrinter().printText(PrinterConst.SMFP_STATION_REC, preLine,
                    new FontNumber(font));
            getParams().preLine = "";
        }
    }

    public void printPostLine() throws Exception {
        String postLine = getParams().postLine;
        if (postLine.length() > 0) 
        {
            int font = getModel().getDeviceFontSmall();
            getPrinter().printText(PrinterConst.SMFP_STATION_REC, postLine,
                    new FontNumber(font));
            getParams().postLine = "";
        }
    }

}
