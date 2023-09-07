package com.mediscreen.riskservice.client;

import com.mediscreen.riskservice.exception.PatientClientException;
import com.mediscreen.riskservice.exception.PatientNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class PatientClientFallbackTest {

    private PatientClientFallbackFactory patientClientFallbackFactory = new PatientClientFallbackFactory();

    @Test
    void findAById() {
        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientClientFallbackFactory.create(mock(FeignException.NotFound.class)).findById(1l));
        assertEquals("Patient with id: 1 could not be found.", exception.getMessage());
    }

    @Test
    void findAById_NotFound() {
        PatientClientException exception = assertThrows(PatientClientException.class, () -> patientClientFallbackFactory.create(mock(Exception.class)).findById(1l));
        assertEquals("Error wile retrieving patient with id: 1", exception.getMessage());
    }
}
