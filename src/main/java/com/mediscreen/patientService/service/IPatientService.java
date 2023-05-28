package com.mediscreen.patientService.service;

import com.mediscreen.patientService.model.Patient;

import java.util.List;

public interface IPatientService {
    void saveNewPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatient(long l);
}
