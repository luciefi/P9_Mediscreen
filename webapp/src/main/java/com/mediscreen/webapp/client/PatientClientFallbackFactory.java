package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.exception.UnavailablePatientClientException;
import com.mediscreen.webapp.model.Patient;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallbackFactory implements FallbackFactory<PatientClient> {

    Logger logger = LoggerFactory.getLogger(PatientClientFallbackFactory.class);

    @Override
    public PatientClient create(Throwable cause) {
        logger.error("An exception occurred when calling the PatientClient", cause);

        return new PatientClient() {

            @Override
            public Page<Patient> findAll(int pageNumber, int itemPerPage) {
                throwPatientClientException("\u26a0 \u2007 Error while retrieving patient list.");
                return null;
            }

            @Override
            public Patient findById(Long id) {
                throwPatientClientException("\u26a0 \u2007 Error while retrieving patient details with id: " + id + ".");
                return null;
            }

            @Override
            public void createPatient(Patient patient) {
                throwPatientClientException("\u26a0 \u2007 Error while saving new patient " + patient.getGivenName() + patient.getFamilyName() + ".");
            }

            @Override
            public void save(Patient patient) {
                throwPatientClientException("\u26a0 \u2007 Error while updating patient " + patient.getGivenName() + patient.getFamilyName() + " with id: " + patient.getId() + ".");
            }

            @Override
            public void deleteById(Long id) {
                throwPatientClientException("\u26a0 \u2007 Error while deleting patient with id: " + id + ".");
            }

            private void throwPatientClientException(String message) {
                if (cause instanceof FeignException.BadRequest) {
                    throw new PatientClientException(message + " (Bad request)");
                }
                if (cause instanceof FeignException.NotFound) {
                    throw new PatientClientException(message + " (Not Found)");
                }
                if (cause instanceof RetryableException) {
                    throw new UnavailablePatientClientException(message + " Service is unavailable.");
                }
                throw new PatientClientException(message);
            }
        };
    }
}
