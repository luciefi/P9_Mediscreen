package com.mediscreen.riskservice.exception;

public class PatientNotFoundException extends IllegalArgumentException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
