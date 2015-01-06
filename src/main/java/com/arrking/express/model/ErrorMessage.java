package com.arrking.express.model;

/**
 * Created by hain on 06/01/2015.
 */
public class ErrorMessage {
    private String errorMessage;
    private String statusCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
