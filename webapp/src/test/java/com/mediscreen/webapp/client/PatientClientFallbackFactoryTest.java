package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientClientFallbackFactoryTest {

    private PatientClientFallbackFactory patientClientFallbackFactory = new PatientClientFallbackFactory();

    @Test
    void findAll() {
        assertNull(patientClientFallbackFactory.findAll(1,1));
    }

    @Test
    void findById() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.findById(1l));
    }

    @Test
    void createPatient() {
        assertDoesNotThrow(() -> patientClientFallbackFactory.createPatient(new Patient()));
    }

    @Test
    void save() {
        Patient patient = new Patient();
        patient.setId(1l);
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.save(patient));
    }

    @Test
    void deleteById() {
        assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.deleteById(1l));
    }
}