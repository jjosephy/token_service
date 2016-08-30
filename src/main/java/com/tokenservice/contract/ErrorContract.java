package com.tokenservice.contract;

import java.util.Date;

public class ErrorContract extends ContractBase {

    private final int errorCode;
    private final String errorMessage;
    private final Date timeStamp;

    public ErrorContract(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timeStamp = new Date();
    }

    public int getCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.errorMessage;
    }
    
    public String getTimeStamp() {
        return this.timeStamp.toString();
    }
}