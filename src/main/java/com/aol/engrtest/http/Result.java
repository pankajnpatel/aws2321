package com.aol.engrtest.http;

/**
 * The class Result used to store response message either it is SUCCESS or FAILUER.
 * @author Pankaj Patel
 */
public class Result {

    private ResponseCode status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseCode getStatus() {
        return status;
    }

    public void setStatus(ResponseCode status) {
        this.status = status;
    }
}
