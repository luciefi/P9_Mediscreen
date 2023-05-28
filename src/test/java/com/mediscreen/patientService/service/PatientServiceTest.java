package com.mediscreen.patientService.service;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void getPatient() {
        // Arrange
        Patient patient = new Patient();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        // Act
        service.getPatient(1l);

        // Assert
        verify(repository, times(1)).findById(1l);
    }

    @Test
    void getPatientNotFound() {
        // Arrange
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act
        assertThrows(PatientNotFoundException.class, () -> service.getPatient(1l));

        // Assert
        verify(repository, times(1)).findById(1l);
    }


    @Test
    void updatePatient() {
        // Arrange
        Patient patient = new Patient();

        // Act
        service.updatePatient(patient);

        // Assert
        verify(repository, times(1)).save(any(Patient.class));
    }

    @Test
    void deletePatients() {
        // Act
        service.deletePatient(1l);

        // Assert
        verify(repository, times(1)).deleteById(1l);
    }
}
