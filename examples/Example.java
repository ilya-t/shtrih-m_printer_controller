package com.example;

import android.app.Activity;

import java.text.DateFormat;
import com.printerhelper.common.BaseCashCheck;
import com.printerhelper.common.BasePrinter;
import com.printerhelper.common.CheckItem;
import com.printerhelper.shtrih.ShtrihPrinter;
import java.util.Arrays;
import java.util.Calendar;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Example extends Activity{
    private ShtrihPrinter printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printer = new ShtrihPrinter(this);

        if (printer.isConfigured() && printer.connectDevice().isClear()){
            printer.printString(DateFormat.getInstance().format(Calendar.getInstance().getTime()) + " : print test");

            BaseCashCheck<CheckItem> check = new BaseCashCheck<>(ShtrihPrinter.PAYMENT_TYPE_CASH);

            check.getItemList().addAll(Arrays.asList(
                    new CheckItem("Potato", 1, 80),
                    new CheckItem("Bread", 5, 0),
                    new CheckItem("Beer", 3, 145.00)
            ));

            printer.printCheck(check, BasePrinter.CheckType.SALE);
            printer.disconnectDevice();
            printer.terminateInstance();
        }else{
            printer.configure(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        printer.onActivityResult(requestCode, resultCode, data);
    }
}
