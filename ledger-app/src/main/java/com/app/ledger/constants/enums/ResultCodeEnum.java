package com.app.ledger.constants.enums;

/**
 * @author jaswin.shah
 * @version $Id: ResultCodeEnum.java, v 0.1 2021-06-21 07:56 AM jaswin.shah Exp $$
 */
public enum ResultCodeEnum {
    /**
     * Success result
     */
    SUCCESS("00000000", "S", "SUCCESS", "Success"),

    SYSTEM_ERROR("00000900", "U", "SYSTEM_ERROR", "System error"),

    INVALID("00000001", "F", "FAILED", "Invalid Params"),

    INTERNAL_SERVER_ERROR("00000009", "F", "FAILED", "Internal Server error"),

    PARAM_ILLEGAL("00000004", "F", "PARAM_ILLEGAL", "Illegal parameters"),

    FORBIDDEN("00000005", "F", "FAILED", "FORBIDDEN"),

    TOO_MANY_REQUESTS("00000006", "F", "TOO_MANY_REQUESTS", "API Hit limit reached");


    private final String resultCodeId;

    private final String resultStatus;

    private final String resultCode;

    private final String resultMsg;

    /**
     * @param resultCodeId enum resultCodeId
     * @param resultStatus enum resultStatus
     * @param resultCode  enum resultCode
     * @param resultMsg enum resultMsg
     */
    ResultCodeEnum(String resultCodeId, String resultStatus,String resultCode,String resultMsg) {
        this.resultCodeId = resultCodeId;
        this.resultStatus = resultStatus;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCodeId() {
        return resultCodeId;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
