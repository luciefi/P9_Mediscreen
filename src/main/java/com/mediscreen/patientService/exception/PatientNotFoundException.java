package com.mediscreen.patientService.exception;


public class PatientNotFoundException  extends NotFoundException {
    public PatientNotFoundException() {
        super("Patient could not be found");
    }
}
