package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.PatientClient;
import com.mediscreen.webapp.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientClient client;

    @Override
    public void saveNewPatient(Patient patient) {
        client.createPatient(patient);
    }

    @Override
    public Page<Patient> getAllPatientsPaginated(int pageNumber, int itemPerPage) {
        return client.findAll(pageNumber, itemPerPage);
    }

    @Override
    public Patient getPatient(long id) {
        return client.findById(id);
    }

    @Override
    public void updatePatient(Patient patient) {
        client.save(patient);
    }

    @Override
    public void deletePatient(long id) {
        client.deleteById(id);
    }
}
