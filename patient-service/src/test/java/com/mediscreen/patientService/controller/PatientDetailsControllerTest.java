package com.mediscreen.patientService.controller;

import com.mediscreen.patientService.exception.PatientNotFoundException;
import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.service.IPatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientDetailsControllerTest {

    @MockBean
    IPatientService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPatientById() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(service.getPatient(anyLong())).thenReturn(patient);

        // Act
        mockMvc.perform(get("/patientDetails/1"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    void getPaginatedPatients() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(service.getAllPatientsPaginated(anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(patient)));

        // Act
        mockMvc.perform(get("/patientDetails/")
                        .param("pageNumber", "1")
                        .param("itemPerPage","3"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getAllPatientsPaginated(1, 3);
    }

    @Test
    void addPatient() throws Exception {
        // Act
        mockMvc.perform(post("/patientDetails").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"familyName\":\"rose\",\n" +
                                "    \"givenName\":\"smith\",\n" +
                                "    \"sex\":\"F\",\n" +
                                "    \"dateOfBirth\":\"2023-06-05\",\n" +
                                "    \"address\":\"roses+adress\",\n" +
                                "    \"phone\":\"0345678987645\"\n" +
                                "}"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).saveNewPatient(any(Patient.class));
    }

    @Test
    void addPatient_InvalidJson() throws Exception {
        // Act
        mockMvc.perform(post("/patientDetails").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"familyName\":\"rose\",\n" +
                                "    \"givenName\":\"smith\",\n" +
                                "    \"sex\":\"F\",\n" +
                                "    \"dateOfBirth\":\"2023-06-05\",\n" +
                                "    \"address\":\"roses+adress\",\n" +
                                "    \"phone\":\"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.never()).saveNewPatient(any(Patient.class));
    }

    @Test
    void updateMedicalRecord() throws Exception {
        // Act
        mockMvc.perform(put("/patientDetails").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"familyName\":\"rose\",\n" +
                                "    \"givenName\":\"smith\",\n" +
                                "    \"sex\":\"F\",\n" +
                                "    \"dateOfBirth\":\"2023-06-05\",\n" +
                                "    \"address\":\"roses+adress\",\n" +
                                "    \"phone\":\"0345678987645\"\n" +
                                "}"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).updatePatient(any(Patient.class));
    }

    @Test
    void updateMedicalRecord_InvalidJson() throws Exception {
        // Act
        mockMvc.perform(put("/patientDetails").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"familyName\":\"rose\",\n" +
                                "    \"givenName\":\"smith\",\n" +
                                "    \"sex\":\"F\",\n" +
                                "    \"dateOfBirth\":\"2023-06-05\",\n" +
                                "    \"address\":\"roses+adress\",\n" +
                                "    \"phone\":\"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.never()).updatePatient(any(Patient.class));
    }

    @Test
    void deleteMedicalRecord() throws Exception {
        // Act
        mockMvc.perform(delete("/patientDetails/1"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).deletePatient(1l);
    }

    @Test
    void handleException() throws Exception {
        // Arrange
        when(service.getPatient(anyLong())).thenThrow(PatientNotFoundException.class);

        // Act
        mockMvc.perform(get("/patientDetails/1"))
                .andExpect(status().isNotFound());

        // Assert
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    void handleInvalidParameterException() throws Exception {

        // Act
        mockMvc.perform(get("/patientDetails/")
                        .param("pageNumber", "1")
                        .param("itemPerPage","-3"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.never()).getAllPatientsPaginated(anyInt(), anyInt());
    }


}