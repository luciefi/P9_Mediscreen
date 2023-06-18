package com.mediscreen.patientService.service;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public Page<Patient> getAllPatientsPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Patient getPatient(long id) {
        return repository.findById(id).orElseThrow(PatientNotFoundException::new);
    }

    @Override
    public void updatePatient(Patient patient) {
        repository.save(patient);
    }

    @Override
    public void deletePatient(long id) {
        repository.deleteById(id);
    }
}
