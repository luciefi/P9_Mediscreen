package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientClientFallbackFactoryTest {

    private PatientClientFallbackFactory patientClientFallbackFactory = new PatientClientFallbackFactory();

    @Test
    void findAll() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).findAll(1, 1));
    }

    @Test
    void findById() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).findById(1l));
    }

    @Test
    void createPatient() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).createPatient(new Patient()));
    }

    @Test
    void save() {
        Patient patient = new Patient();
        patient.setId(1l);
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).save(patient));
    }

    @Test
    void deleteById() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(new Throwable()).deleteById(1l));
    }
}