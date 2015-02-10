package com.printerhelper.shtrih;

import android.text.TextUtils;
import android.util.Log;

import com.printerhelper.common.BaseDeviceSettings;
import com.shtrih.jpos.JposDeviceStatistics;
import com.shtrih.util.SysUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import jpos.FiscalPrinter;
import jpos.JposException;

public class ShtrihDeviceSettings implements BaseDeviceSettings{
    private String macAddress;

    private String deviceName;
    private String unifiedPOSVersion;
    private String deviceCategory;
    private String manufacturerName;
    private String modelName;
    private String serialNumber;
    private String firmwareRevision;
    private String physicalInterface;
    private long dateTime;
    private PrintError error;


    public PrintError update(ShtrihPrinter shtrihPrinter) {
        String[] buffer = new String[1];
        buffer[0] = "";
        try {
            FiscalPrinter printer = shtrihPrinter.getPrinter();
            printer.retrieveStatistics(buffer);
            JposDeviceStatistics ds = new JposDeviceStatistics();
            String fileName = "device_"+Calendar.getInstance().getTimeInMillis()+".xml";
            File file = null;
            try {
                file = new File(SysUtils.getFilesPath() + fileName);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(buffer[0]);
                    writer.close();
                } catch (IOException e) {
                    Log.w("Debug", e.getMessage(), e);
                    file.delete();
                    return new PrintError(e);
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (file != null && file.exists()){
                    file.delete();
                }
                return new PrintError(e);
            }
            ds.load(fileName);

            if (file != null && file.exists()){
                file.delete();
            }

            unifiedPOSVersion = ds.unifiedPOSVersion;
            deviceCategory = ds.deviceCategory;
            manufacturerName = ds.manufacturerName;
            modelName = ds.modelName;
            serialNumber = ds.serialNumber;
            firmwareRevision = ds.firmwareRevision;
            physicalInterface = ds.physicalInterface;
            dateTime = shtrihPrinter.getPrinterTimeInMillis();
        } catch (JposException e) {
            e.printStackTrace();
            return new PrintError(e);
        }

        return PrintError.success;
    }

    public ShtrihDeviceSettings(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String getDeviceConfig() {
        return this.macAddress;
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public boolean isConfigured() {
        return !TextUtils.isEmpty(macAddress);
    }

    @Override
    public long getDateTime() {
        return dateTime;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getModelName() {
        return modelName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    @Override
    public String toString() {
/*
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateTime);

        return
                "unifiedPOSVersion: "+String.valueOf(unifiedPOSVersion)+"\n"+
                "deviceCategory: "+String.valueOf(deviceCategory)+"\n"+
                "manufacturerName: "+String.valueOf(manufacturerName)+"\n"+
                "modelName: "+String.valueOf(modelName)+"\n"+
                "serialNumber: "+String.valueOf(serialNumber)+"\n"+
                "firmwareRevision: "+String.valueOf(firmwareRevision)+"\n"+
                "physicalInterface: "+String.valueOf(physicalInterface)+"\n"+
                "dateTime: "+ c.getTime().toString()+ " / " +String.valueOf(dateTime)+"\n";
*/
        return super.toString();
    }

    public void setError(PrintError error) {
        this.error = error;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
