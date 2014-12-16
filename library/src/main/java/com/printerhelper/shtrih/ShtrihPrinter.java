package com.printerhelper.shtrih;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.printerhelper.common.BaseCashCheck;
import com.printerhelper.common.BaseDeviceSettings;
import com.printerhelper.common.BasePrintError;
import com.printerhelper.common.BasePrinter;
import com.printerhelper.common.CheckItem;
import com.printerhelper.common.DefaultSettingsContainer;
import com.printerhelper.common.SettingsContainer;
import com.shtrih.fiscalprinter.ShtrihFiscalPrinter;
import com.shtrih.tinyjavapostester.ConfigureLog4J;
import com.shtrih.tinyjavapostester.DeviceListActivity;
import com.shtrih.tinyjavapostester.JposConfig;
import com.shtrih.util.StaticContext;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jpos.FiscalPrinter;
import jpos.FiscalPrinterConst;
import jpos.JposConst;
import jpos.JposException;

public class ShtrihPrinter implements BasePrinter{
    public final static String PAYMENT_TYPE_UNKNOWN = "-1";
    public final static String PAYMENT_TYPE_CASH = "0";
    public final static String PAYMENT_TYPE_CASHLESS_1 = "10";
    public final static String PAYMENT_TYPE_CASHLESS_2 = "20";
    public final static String PAYMENT_TYPE_CASHLESS_3 = "30";
    public final static String PAYMENT_TYPE_CREDIT = PAYMENT_TYPE_CASHLESS_1;
    public final static String PAYMENT_TYPE_PACKAGE = PAYMENT_TYPE_CASHLESS_2;
    public final static String PAYMENT_TYPE_CARD = PAYMENT_TYPE_CASHLESS_3;

    private static final int ERROR_CODE_UNSUPPORTED_PAYMENT_TYPE = -100;
    private static Logger logger = Logger.getLogger(ShtrihPrinter.class.getSimpleName());
    private FiscalPrinter printer = null;
    private final static String TAG = ShtrihPrinter.class.getSimpleName();
    private boolean isConfigured;
    private SettingsContainer settingsContainer;
    private ShtrihDeviceSettings connectionSettings;

    public ShtrihPrinter(Context context) {
        // readModelsXml();
        ConfigureLog4J.configure();
        StaticContext.setContext(context.getApplicationContext());
        printer = new FiscalPrinter();
        settingsContainer = (this instanceof SettingsContainer)
                ?(SettingsContainer)this
                :new DefaultSettingsContainer(context);
    }

    @Override
    public void configure(Activity activity) {
        Intent i = new Intent(activity, DeviceListActivity.class);
        activity.startActivityForResult(i, DeviceListActivity.REQUEST_CONNECT_BT_DEVICE);
    }

    @Override
    public boolean isConnected() {
        try {
            return printer.getDeviceEnabled();
        } catch (JposException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BasePrintError connectDevice() {
        try {
            if (printer.getState() != JposConst.JPOS_S_CLOSED) {
                printer.close();
            }

            BasePrintError error = applyDeviceInfo(getConnectionSettings().getMacAddress());
            if (error != null && !error.isClear()){
                return error;
            }

            printer.open("ShtrihFptr");
            printer.claim(3000);
            printer.setDeviceEnabled(true);
            return PrintError.success;
        } catch (JposException e) {
            e.printStackTrace();
            return new PrintError(e);
        }
    }

    @Override
    public BasePrintError cancelCheck() {
        try {
            printer.printRecVoid("---");
            printer.endFiscalReceipt(false);
            return PrintError.success;
        } catch (JposException e) {
            e.printStackTrace();
            return new PrintError(e);
        }
    }

    @Override
    public BasePrintError printCheck(BaseCashCheck<? extends CheckItem> cashCheck, CheckType checkType) {
        try {
            ShtrihFiscalPrinter shtrihFiscalPrinter = new ShtrihFiscalPrinter(printer);
            shtrihFiscalPrinter.resetPrinter();
            SparseArray<Integer> vatList = getVatList();

            for (CheckItem checkItem : cashCheck.getItemList()){
                if (checkItem.getVatAmount() > 0){
                    if (vatList.indexOfValue(checkItem.getVatAmount()*100) != -1){
                        return new PrintError("Printer does not support VAT "+checkItem.getVatAmount());
                    }
                }
            }

            switch (checkType) {
                case SALE:
                    shtrihFiscalPrinter.setFiscalReceiptType(jpos.FiscalPrinterConst.FPTR_RT_SALES);
                    break;
                case REFUND:
                    shtrihFiscalPrinter.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_REFUND);
                    break;
                default:
                    return new PrintError("check type not supported");
            }

            shtrihFiscalPrinter.beginFiscalReceipt(false);

            if (cashCheck.getHeaders() != null){
                for (String header : cashCheck.getHeaders()){
                    shtrihFiscalPrinter.printRecMessage(header);
                }
            }
            long totalSum = 0;
            int decimalPlaces = (int) Math.pow(10, shtrihFiscalPrinter.getQuantityDecimalPlaces());


            for (CheckItem checkItem : cashCheck.getItemList()){
                long price = ((Double) checkItem.getPrice()).longValue()*100;
                int quantity = ((Double) checkItem.getQuantity()).intValue();

                int vatIndex = vatList.indexOfValue(checkItem.getVatAmount() * 100);
                if (vatIndex < 1){
                    vatIndex = 0;
                }

                shtrihFiscalPrinter.setDepartment(checkItem.getDepartment());

                switch (checkType) {
                    case SALE:
                        shtrihFiscalPrinter.printRecItem(checkItem.getTitle(), 0, quantity * decimalPlaces, vatIndex, price, "");
                        break;
                    case REFUND:
                        shtrihFiscalPrinter.printRecItemVoid(checkItem.getTitle(), 0, quantity * decimalPlaces, vatIndex, price, "");
                        break;
                    default:
                        return new PrintError("check type not supported");
                }

                if (checkItem.getHeaders() != null){
                    for (String header : checkItem.getHeaders()){
                        shtrihFiscalPrinter.printRecMessage(header);
                    }
                }
                totalSum += price * quantity;
            }

            String paymentType = cashCheck.getPaymentType();
            if (this instanceof ShtrihPaymentTypeParser){
                paymentType = ((ShtrihPaymentTypeParser)this).parseShtrihPaymentType(cashCheck);
            }

            if (paymentType.equals(PAYMENT_TYPE_UNKNOWN)){
                return new PrintError(ERROR_CODE_UNSUPPORTED_PAYMENT_TYPE,"unsupported payment type");
            }
            shtrihFiscalPrinter.printRecTotal(totalSum, totalSum, paymentType);
            shtrihFiscalPrinter.endFiscalReceipt(false);
            cashCheck.setCheckTime(getPrinterTimeInMillis());
            String[] data = new String[1];
            shtrihFiscalPrinter.getData(FiscalPrinterConst.FPTR_GD_RECEIPT_NUMBER, null, data);
            try {
                int checkNumber = Integer.parseInt(data[0]);
                cashCheck.setCheckNumber(checkNumber);
            } catch (NumberFormatException e) {
                return new PrintError(e);
            }


        } catch (JposException e) {
            e.printStackTrace();
            cancelCheck();
            return new PrintError(e);
        }
        return PrintError.success;
    }

    public SparseArray<Integer> getVatList() {
        SparseArray<Integer> result = new SparseArray<>();
        int[] args = new int[1];

        try {
            if (getPrinter().getCapSetVatTable()){
                int vatEntryCount = getPrinter().getNumVatRates();

                for (int i = 1; i < vatEntryCount+1; i++) {
                    getPrinter().getVatEntry(i, 0, args);
                    result.put(i, args[0]);
                }
            }
        } catch (JposException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public BasePrintError printString(String line) {
        return new PrintError("method unsupported");//printer.printFiscalDocumentLine();
    }

    @Override
    public BasePrintError reportX() {
        try {
            printer.printXReport();
        } catch (Exception e) {
            e.printStackTrace();
            return new PrintError(e);
        }
        return PrintError.success;
    }

    @Override
    public BasePrintError reportZ() {
        try {
            printer.printZReport();
        } catch (Exception e) {
            e.printStackTrace();
            return new PrintError(e);
        }
        return PrintError.success;
    }

    @Override
    public BasePrintError disconnectDevice() {
        try {
            printer.setDeviceEnabled(false);
            return PrintError.success;
        } catch (JposException e) {
            e.printStackTrace();
            return new PrintError(e);
        }

    }

    @Override
    public void terminateInstance() {
        try {
            printer.release();
            printer.close();
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BasePrintError applyDeviceInfo(String address) {
        try {
            JposConfig.configure("ShtrihFptr", address, getJposConfigFileName());
            isConfigured = true;

            if (getConnectionSettings().getMacAddress().equals("")){
                getConnectionSettings().setMacAddress(address);
                settingsContainer.saveDeviceSettings(getConnectionSettings());
            }

            return PrintError.success;
        } catch (Exception e) {
            e.printStackTrace();
            return new PrintError(e);
        }
    }

    protected String getJposConfigFileName() {
        return "jpos.xml";
    }

    @Override
    public BaseDeviceSettings getDeviceInfo() {
        ShtrihDeviceSettings ds;
        if (connectionSettings != null){
            ds = new ShtrihDeviceSettings(connectionSettings.getMacAddress());
            ds.setError(ds.update(this));
        }else{
            ds = new ShtrihDeviceSettings("");
            ds.setError(new PrintError("unable to connect to device"));
        }
        return ds;
    }

    @Override
    public long getPrinterTimeInMillis() {
        String[] date = new String[1];
        try {
            printer.setDateType(FiscalPrinterConst.FPTR_DT_RTC);
            printer.getDate(date);
        } catch (JposException e) {
            e.printStackTrace();
            return -1;
        }

        DateFormat df = new SimpleDateFormat("ddMMyyyyHHmm");
        try {
            Date dt = df.parse(date[0]);
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (
                resultCode == Activity.RESULT_OK &&
                requestCode == DeviceListActivity.REQUEST_CONNECT_BT_DEVICE &&
                data != null && data.getExtras() != null &&
                data.getExtras().containsKey(DeviceListActivity.EXTRA_DEVICE_ADDRESS)) {
            String macAddress = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
            getConnectionSettings().setMacAddress(macAddress);

            settingsContainer.saveDeviceSettings(connectionSettings);
        }
    }

    private ShtrihDeviceSettings getConnectionSettings() {
        if (connectionSettings == null){
            connectionSettings = new ShtrihDeviceSettings(settingsContainer.getConnectSettings());
        }
        return connectionSettings;
    }


    public FiscalPrinter getPrinter() {
        return printer;
    }
}
