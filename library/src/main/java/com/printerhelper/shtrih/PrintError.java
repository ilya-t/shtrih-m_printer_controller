package com.printerhelper.shtrih;

import com.printerhelper.common.BasePrintError;
import jpos.JposConst;
import jpos.JposException;

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
        this.errorCode = (error instanceof JposException) ? ((JposException) error).getErrorCode() : CODE_FAIL;
        this.errorDesc = error.getMessage() != null ? error.getMessage() : error.toString();

        if (error.getMessage() == null){
            switch (errorCode){
                case JposConst.JPOS_E_CLOSED: this.errorDesc = "An attempt was made to access a closed JavaPOS Device.";
                case JposConst.JPOS_E_CLAIMED: this.errorDesc = "An attempt was made to access a Physical Device that is claimed by another Device Control instance. The other Control must release the Physical Device before this access may be made. For exclusive-use devices, the application will also need to claim the Physical Device before the access is legal.";
                case JposConst.JPOS_E_NOTCLAIMED: this.errorDesc = "An attempt was made to access an exclusive-use device that must be claimed before the method or property set action can be used. If the Physical Device is already claimed by another Device Control instance, then the status JPOS_E_CLAIMED is returned instead.";
                case JposConst.JPOS_E_NOSERVICE: this.errorDesc = "The Control cannot communicate with the Service, normally because of a setup or configuration error.";
                case JposConst.JPOS_E_DISABLED: this.errorDesc = "Cannot perform this operation while the Device is disabled.";
                case JposConst.JPOS_E_ILLEGAL: this.errorDesc = "An attempt was made to perform an illegal or unsupported operation with the Device, or an invalid parameter value was used.";
                case JposConst.JPOS_E_NOHARDWARE: this.errorDesc = "The Physical Device is not connected to the system or is not powered on.";
                case JposConst.JPOS_E_OFFLINE: this.errorDesc = "The Physical Device is off-line.";
                case JposConst.JPOS_E_NOEXIST: this.errorDesc = "The file name (or other specified value) does not exist.";
                case JposConst.JPOS_E_EXISTS: this.errorDesc = "The file name (or other specified value) already exists.";
                case JposConst.JPOS_E_FAILURE: this.errorDesc = "The Device cannot perform the requested procedure, even though the Physical Device is connected to the system, powered on, and on-line.";
                case JposConst.JPOS_E_TIMEOUT: this.errorDesc = "The Service timed out waiting for a response from the Physical Device, or the Control timed out waiting for a response from the Service.";
                case JposConst.JPOS_E_BUSY: this.errorDesc = "The current Device Service state does not allow this request. For example, if asynchronous output is in progress, certain methods may not be allowed.";
                case JposConst.JPOS_E_EXTENDED: this.errorDesc = "A device category-specific error condition occurred. The error condition code is available by calling getErrorCodeExtended.";
                case JposConst.JPOS_E_DEPRECATED: this.errorDesc = "ERROR DEPRECATED";
            }
        }
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
