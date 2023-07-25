package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientNotFoundException;
import com.mediscreen.webapp.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallback implements PatientClient {

    Logger logger = LoggerFactory.getLogger(PatientClientFallback.class);

    @Override
    public Page<Patient> findAll(int pageNumber, int itemPerPage) {
        return null;
    }

    @Override
    public Patient findById(Long id) {
        throwPatientNotFound(id);
        return null;
    }

    @Override
    public void createPatient(Patient patient) {
    }

    @Override
    public void save(Patient patient) {
        throwPatientNotFound(patient.getId());
    }

    @Override
    public void deleteById(Long id) {
        throwPatientNotFound(id);
    }

    private void throwPatientNotFound(long id) {
        logger.info("Patient with id: " + id + " could not be found.");
        throw new PatientNotFoundException();
    }
}
