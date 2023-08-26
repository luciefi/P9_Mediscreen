package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallback implements PatientClient {

    @Override
    public Page<Patient> findAll(int pageNumber, int itemPerPage) {
        throwPatientClientException( "Exception while retrieving patient list from patient service.");
        return null;
    }

    @Override
    public Patient findById(Long id) {
        throwPatientClientException("Exception while retrieving patient details with id: " + id + " from patient service.");
        return null;
    }

    @Override
    public void createPatient(Patient patient) {
        throwPatientClientException("Exception while saving new patient " + patient.getGivenName() + patient.getFamilyName() + " in patient service.");
    }

    @Override
    public void save(Patient patient) {
        throwPatientClientException("Exception while updating patient " + patient.getGivenName() + patient.getFamilyName() + " with id: " + patient.getId() + " in patient service.");
    }

    @Override
    public void deleteById(Long id) {
        throwPatientClientException("Exception while deleting patient with id: " + id + " from patient service.");
    }

    private void throwPatientClientException(String message) {
        throw new PatientClientException(message);
    }
}
