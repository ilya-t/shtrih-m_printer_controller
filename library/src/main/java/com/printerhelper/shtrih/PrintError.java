package com.printerhelper.shtrih;

import com.printerhelper.common.BasePrintError;

public class PrintError implements BasePrintError {
    private static final int CODE_FAIL = -1;
    private static final int CODE_SUCCESS = 0;

    @Override
    public String toString() {
        return errorDesc+"("+errorCode+")";
    }

    public static final PrintError success = new PrintError(CODE_SUCCESS, "Completed");
    private int errorCode;
    private String errorDesc;

    public PrintError(Exception error) {
        this.errorCode = CODE_FAIL;
        this.errorDesc = error.getMessage() != null ? error.getMessage() : error.toString();
    }

    public PrintError(int code, String description) {
        errorCode = code;
        errorDesc = description;
    }

    public PrintError(String description) {
        errorCode = CODE_FAIL;
        errorDesc = description;
    }

    @Override
    public boolean isClear(){
        return errorCode == CODE_SUCCESS;
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
