package com.printerhelper.shtrih;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.printerhelper.common.BaseCashCheck;
import com.printerhelper.common.BaseDeviceSettings;
import com.printerhelper.common.BasePrintError;
import com.printerhelper.common.BasePrinter;
import com.printerhelper.common.CheckItem;
import com.shtrih.tinyjavapostester.ConfigureLog4J;
import com.shtrih.tinyjavapostester.DeviceListActivity;
import com.shtrih.util.StaticContext;

import org.apache.log4j.Logger;

import jpos.FiscalPrinter;

public class ShtrihPrinter implements BasePrinter{
    private static Logger logger = Logger.getLogger(ShtrihPrinter.class.getSimpleName());
    private FiscalPrinter printer = null;
    private final static String TAG = ShtrihPrinter.class.getSimpleName();

    public ShtrihPrinter(Context context) {
        // readModelsXml();
        ConfigureLog4J.configure();
        StaticContext.setContext(context.getApplicationContext());
        printer = new FiscalPrinter();
    }

    @Override
    public void configure(Activity activity) {
        Intent i = new Intent(activity, DeviceListActivity.class);
        activity.startActivityForResult(i, DeviceListActivity.REQUEST_CONNECT_BT_DEVICE);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public BasePrintError connectDevice() {
        return null;
    }

    @Override
    public BasePrintError cancelCheck() {
        return null;
    }

    @Override
    public BasePrintError printCheck(BaseCashCheck<? extends CheckItem> cashCheck, CheckType checkType) {
        return null;
    }

    @Override
    public BasePrintError printString(String line) {
        return null;
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
    public void disconnectDevice() {

    }

    @Override
    public void terminateInstance() {

    }

    @Override
    public BasePrintError applyDeviceInfo(String deviceInfo) {
        return null;
    }

    @Override
    public BaseDeviceSettings getDeviceInfo() {
        return null;
    }

    @Override
    public long getPrinterTimeInMillis() {
        return 0;
    }

    @Override
    public boolean isConfigured() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
