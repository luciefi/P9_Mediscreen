package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.exception.UnavailablePatientClientException;
import com.mediscreen.webapp.model.Patient;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PatientClientFallbackFactoryTest {

    private PatientClientFallbackFactory patientClientFallbackFactory = new PatientClientFallbackFactory();

    @Test
    void findAll() {
        PatientClientException exception = assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(mock(FeignException.BadRequest.class)).findAll(1, 1));
        assertEquals("\u26a0 \u2007 Error while retrieving patient list. (Bad request)", exception.getMessage());
    }

    @Test
    void findById() {
        PatientClientException exception = assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(mock(FeignException.NotFound.class)).findById(1l));
        assertEquals("\u26a0 \u2007 Error while retrieving patient details with id: 1. (Not found)", exception.getMessage());
    }

    @Test
    void createPatient() {
        UnavailablePatientClientException exception = assertThrows(UnavailablePatientClientException.class, () -> patientClientFallbackFactory.create(mock(RetryableException.class)).createPatient(new Patient()));
        assertEquals("\u26a0 \u2007 Error while saving new patient null null. Service is unavailable.", exception.getMessage());
    }

    @Test
    void save() {
        Patient patient = new Patient();
        patient.setId(1l);
        Throwable exception = assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).save(patient));
        assertEquals("\u26a0 \u2007 Error while updating patient null null with id: 1.", exception.getMessage());
    }

    @Test
    void deleteById() {
        Throwable exception = assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).deleteById(1l));
        assertEquals("\u26a0 \u2007 Error while deleting patient with id: 1.", exception.getMessage());
    }
}