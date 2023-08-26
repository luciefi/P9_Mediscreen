package com.mediscreen.riskservice.exception;


public class PatientNotFoundException  extends NotFoundException {
    public PatientNotFoundException() {
        super("Patient could not be found");
    }
}
