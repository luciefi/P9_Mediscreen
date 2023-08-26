package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientClientFallbackTest {

    private PatientClientFallback patientClientFallback = new PatientClientFallback();

    @Test
    void findAll() {
        assertNull(patientClientFallback.findAll(1,1));
    }

    @Test
    void findById() {
        assertThrows(PatientClientException.class, () -> patientClientFallback.findById(1l));
    }

    @Test
    void createPatient() {
        assertDoesNotThrow(() -> patientClientFallback.createPatient(new Patient()));
    }

    @Test
    void save() {
        Patient patient = new Patient();
        patient.setId(1l);
        assertThrows(PatientClientException.class, () -> patientClientFallback.save(patient));
    }

    @Test
    void deleteById() {
        assertThrows(PatientClientException.class, () -> patientClientFallback.deleteById(1l));
    }
}