package com.printerhelper.shtrih;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
            printer.printRecVoid("");
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

//            printer.printNormal(FiscalPrinterConst.FPTR_S_RECEIPT, itemName);

            long totalSum = 0;
            for (CheckItem checkItem : cashCheck.getItemList()){
                long price = ((Double) checkItem.getPrice()).longValue()*100;
                int decimalPlaces = 1000;
                int quantity = ((Double) checkItem.getQuantity()).intValue();
                String info = String.valueOf(printer.getQuantityDecimalPlaces())+"/"+String.valueOf(printer.getQuantityLength());// 3 / 10
                printer.printRecItem(checkItem.getTitle(), 0, quantity*decimalPlaces, 0, price, "шт.");
                totalSum += price * quantity;
            }



    /*            for (int i = 0; i < howMuch; i++) {
                    long price = Math.abs(rand.nextLong() % 1000);
                    payment += price;

                    String itemName = items[rand.nextInt(items.length)];
                    printer.printNormal(FiscalPrinterConst.FPTR_S_RECEIPT, itemName);
                    printer.printRecItem(itemName, price, 0, 0, 0, "");
                }
                printer.printRecTotal(payment, payment, "1");
    */
            printer.printRecTotal(totalSum, totalSum, "(PAID)");
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

        DateFormat df = new SimpleDateFormat("ddmmyyyyHHmm");
        try {
            Date dt = df.parse(date[0]);
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
