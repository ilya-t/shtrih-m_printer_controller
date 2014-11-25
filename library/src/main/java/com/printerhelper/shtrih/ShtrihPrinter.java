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
    private static Logger logger = Logger.getLogger(ShtrihPrinter.class.getSimpleName());
    private FiscalPrinter printer = null;
    private final static String TAG = ShtrihPrinter.class.getSimpleName();
    private boolean isConfigured;
    private SettingsContainer settingsContainer;
    private DeviceSettings connectionSettings;

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
            printer.resetPrinter();
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
                    printer.setFiscalReceiptType(jpos.FiscalPrinterConst.FPTR_RT_SALES);
                    break;
                case REFUND:
                    printer.setFiscalReceiptType(FiscalPrinterConst.FPTR_RT_REFUND);
                    break;
                default:
                    return new PrintError("check type not supported");
            }

            printer.beginFiscalReceipt(false);

            if (cashCheck.getHeaders() != null){
                for (String header : cashCheck.getHeaders()){
                    printer.printRecMessage(header);
                }
            }
            long totalSum = 0;
            int decimalPlaces = (int) Math.pow(10, printer.getQuantityDecimalPlaces());
            for (CheckItem checkItem : cashCheck.getItemList()){
                long price = ((Double) checkItem.getPrice()).longValue()*100;
                int quantity = ((Double) checkItem.getQuantity()).intValue();

                int vatIndex = vatList.indexOfValue(checkItem.getVatAmount() * 100);
                if (vatIndex < 1){
                    vatIndex = 0;
                }

                switch (checkType) {
                    case SALE:
                        printer.printRecItem(checkItem.getTitle(), 0, quantity * decimalPlaces, vatIndex, price, "");
                        break;
                    case REFUND:
                        printer.printRecItemVoid(checkItem.getTitle(), 0, quantity * decimalPlaces, vatIndex, price, "");
                        break;
                    default:
                        return new PrintError("check type not supported");
                }

                if (checkItem.getHeaders() != null){
                    for (String header : checkItem.getHeaders()){
                        printer.printRecMessage(header);
                    }
                }
                totalSum += price * quantity;
            }

            printer.printRecTotal(totalSum, totalSum, "");
            printer.endFiscalReceipt(false);
            cashCheck.setCheckTime(getPrinterTimeInMillis());
            String[] data = new String[1];
            printer.getData(FiscalPrinterConst.FPTR_GD_RECEIPT_NUMBER, null, data);
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
            JposConfig.configure("ShtrihFptr", address);
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

    @Override
    public BaseDeviceSettings getDeviceInfo() {
        DeviceSettings ds;
        if (connectionSettings != null){
            ds = new DeviceSettings(connectionSettings.getMacAddress());
            ds.setError(ds.update(this));
        }else{
            ds = new DeviceSettings("");
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

    private DeviceSettings getConnectionSettings() {
        if (connectionSettings == null){
            connectionSettings = new DeviceSettings(settingsContainer.getConnectSettings());
        }
        return connectionSettings;
    }


    public FiscalPrinter getPrinter() {
        return printer;
    }


}
