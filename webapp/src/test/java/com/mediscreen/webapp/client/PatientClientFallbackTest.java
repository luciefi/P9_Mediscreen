package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.PatientNotFoundException;
import com.mediscreen.webapp.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PatientClientFallbackTest {

    private PatientClientFallback patientClientFallback = new PatientClientFallback();

    @Test
    void findAll() {
        assertNull(patientClientFallback.findAll(1,1));
    }

    @Test
    void findById() {
        assertThrows(PatientNotFoundException.class, () -> patientClientFallback.findById(1l));
    }

    @Test
    void createPatient() {
        assertDoesNotThrow(() -> patientClientFallback.createPatient(new Patient()));
    }

    @Test
    void save() {
        Patient patient = new Patient();
        patient.setId(1l);
        assertThrows(PatientNotFoundException.class, () -> patientClientFallback.save(patient));
    }

    @Test
    void deleteById() {
        assertThrows(PatientNotFoundException.class, () -> patientClientFallback.deleteById(1l));
    }
}