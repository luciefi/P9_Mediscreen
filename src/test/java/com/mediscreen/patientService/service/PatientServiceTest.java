package com.mediscreen.patientService.service;

import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class PatientServiceTest {
    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientService service;

    @Test
    void saveNewPatient() {
        // Arrange
        Patient patient = new Patient();

        // Act
        service.saveNewPatient(patient);

        // Assert
        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void getAllPatients() {
        // Act
        service.getAllPatients();

        // Assert
        verify(repository, times(1)).findAll();
    }
}
