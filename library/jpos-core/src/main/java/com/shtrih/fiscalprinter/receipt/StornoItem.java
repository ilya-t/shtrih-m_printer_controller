/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.receipt;

import com.shtrih.fiscalprinter.SMFiscalPrinter;
import com.shtrih.fiscalprinter.command.PriceItem;

/**
 * @author V.Kravtsov
 */
public class StornoItem implements ReceiptItem {

    private final PriceItem item;
    private long discount = 0;

    public StornoItem(PriceItem item) {
        this.item = item;
    }

    public void print(SMFiscalPrinter printer) throws Exception {
        printer.printVoidItem(item);
    }

    public int getId() {
        return RECEIPT_ITEM_VOID_SALE;
    }

    public String getDescription() {
        return item.getText();
    }

    public long getAmount() {
        return PrinterAmount.getAmount(item.getPrice(), item.getQuantity());
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long value) {
        discount = value;
    }

    public void addDiscount(long amount) {
        discount += amount;
    }
}
