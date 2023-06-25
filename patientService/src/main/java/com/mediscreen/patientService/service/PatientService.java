package com.mediscreen.patientService.service;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientDetailsRepository repository;

    @Override
    public void saveNewPatient(Patient patient) {
        repository.save(patient);
    }

    @Override
    public Page<Patient> getAllPatientsPaginated(int pageNumber, int itemPerPage) {
        return repository.findAll(PageRequest.of(pageNumber, itemPerPage));
    }

    @Override
    public Patient getPatient(long id) {
        return repository.findById(id).orElseThrow(PatientNotFoundException::new);
    }

    @Override
    public void updatePatient(Patient patient) {
        getPatient(patient.getId());
        repository.save(patient);
    }

    @Override
    public void deletePatient(long id) {
        getPatient(id);
        repository.deleteById(id);
    }
}
