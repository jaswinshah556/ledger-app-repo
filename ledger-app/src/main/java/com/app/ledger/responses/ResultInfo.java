package com.app.ledger.responses;

import com.app.ledger.constants.enums.ResultCodeEnum;
import lombok.Data;
import java.io.Serializable;

/**
 * @author jaswin.shah
 * @version $Id: ResultInfo.java, v 0.1 2021-06-21 12:49 AM jaswin.shah Exp $$
 */
@Data
public class ResultInfo implements Serializable {
    private String resultCodeId;
    private String resultStatus;
    private String resultMsg;
    private String resultCode;

    public ResultInfo(String resultStatus, String resultCodeId, String resultCode, String resultMsg) {
        this.resultStatus = resultStatus;
        this.resultCodeId = resultCodeId;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public ResultInfo(ResultCodeEnum resultCodeEnum) {
        this.resultStatus = resultCodeEnum.getResultStatus();
        this.resultCodeId = resultCodeEnum.getResultCodeId();
        this.resultCode = resultCodeEnum.getResultCode();
        this.resultMsg = resultCodeEnum.getResultMsg();
    }

    public ResultInfo(ResultCodeEnum resultCodeEnum, String message) {
        this.resultStatus = resultCodeEnum.getResultStatus();
        this.resultCodeId = resultCodeEnum.getResultCodeId();
        this.resultCode = resultCodeEnum.getResultCode();
        this.resultMsg = message;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultInfo [resultStatus=").append(this.resultStatus).append(", resultCodeId=").append(this.resultCodeId).append(", resultCode=").append(this.resultCode).append(", resultMsg=").append(this.resultMsg).append("]");
        return builder.toString();
    }
}
