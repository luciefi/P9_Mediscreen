package com.mediscreen.patientService.service;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository repository;

    @Override
    public void saveNewPatient(Patient patient) {
        repository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    @Override
    public Patient getPatient(long id) {
        return repository.findById(id).orElseThrow(PatientNotFoundException::new);
    }
}
