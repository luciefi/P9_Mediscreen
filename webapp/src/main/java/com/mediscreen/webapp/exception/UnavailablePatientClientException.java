package com.mediscreen.webapp.exception;


public class UnavailablePatientClientException extends ClientException {

    public UnavailablePatientClientException() {
        super("Unavailable patient client exception.");
    }

    public UnavailablePatientClientException(String message) {
        super(message);
    }
}
