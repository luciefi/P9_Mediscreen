package com.mediscreen.webapp.exception;


public class RiskClientException extends ClientException {
    public RiskClientException() {
        super("Risk client exception.");
    }

    public RiskClientException(String message) {
        super(message);
    }
}
