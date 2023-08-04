package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.NoteClient;
import com.mediscreen.webapp.client.PatientClient;
import com.mediscreen.webapp.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class PatientServiceTest {

    @Mock
    private PatientClient client;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private PatientService service;

    @Test
    void saveNewPatient() {
        // Arrange
        Patient patient = new Patient();

        // Act
        service.saveNewPatient(patient);

        // Assert
        verify(client, times(1)).createPatient(any(Patient.class));
    }

    @Test
    void getAllPatientsPaginated() {
        // Act
        Page<Patient> patients = service.getAllPatientsPaginated(0, 2);

        // Assert
        verify(client, times(1)).findAll(0, 2);
    }

    @Test
    void getPatient() {
        // Arrange
        Patient patient = new Patient();
        when(client.findById(any(Long.class))).thenReturn(patient);

        // Act
        service.getPatient(1l);

        // Assert
        verify(client, times(1)).findById(1l);
    }

    @Test
    void updatePatient() {
        // Arrange
        Patient patient = new Patient();

        // Act
        service.updatePatient(patient);

        // Assert
        verify(client, times(1)).save(any(Patient.class));
    }

    @Test
    void deletePatients() {
        // Act
        service.deletePatient(1l);

        // Assert
        verify(client, times(1)).deleteById(1l);
        verify(noteClient, times(1)).deleteByPatientId(1l);
    }

}
