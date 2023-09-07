package com.mediscreen.riskservice.exception;


public class PatientClientException extends ClientException {
    public PatientClientException() {
        super("Patient client exception.");
    }

    public PatientClientException(String message) {
        super(message);
    }
}
