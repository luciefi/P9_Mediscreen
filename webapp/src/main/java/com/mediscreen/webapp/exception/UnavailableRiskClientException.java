package com.mediscreen.webapp.exception;


public class UnavailableRiskClientException extends ClientException {

    public UnavailableRiskClientException() {
        super("Unavailable risk client exception.");
    }

    public UnavailableRiskClientException(String message) {
        super(message);
    }
}
