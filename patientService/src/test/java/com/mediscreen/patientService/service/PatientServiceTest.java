package com.mediscreen.patientService.service;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.repository.PatientDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PatientServiceTest {

    @Mock
    private PatientDetailsRepository repository;

    @InjectMocks
    private PatientService service;


    @Test
    void saveNewPatient() {
        // Arrange
        Patient patient = new Patient();

        // Act
        service.saveNewPatient(patient);

        // Assert
        verify(repository, times(1)).save(patient);
    }

    @Test
    void getAllPatientsPaginated() {
        // Act
        Page<Patient> patient = service.getAllPatientsPaginated(0, 3);

        // Assert
        verify(repository, times(1)).findAll(any(Pageable.class));
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
    void getPatient_NotFound() {
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
        patient.setId(1L);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        // Act
        service.updatePatient(patient);

        // Assert
        verify(repository, times(1)).findById(1l);
        verify(repository, times(1)).save(patient);
    }

    @Test
    void deletePatient() {
        // Arrange
        Patient patient = new Patient();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(patient));

        // Act
        service.deletePatient(1l);

        // Assert
        verify(repository, times(1)).findById(1l);
        verify(repository, times(1)).deleteById(1l);
    }
}