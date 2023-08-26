package com.mediscreen.webapp.integration.controller;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.service.IPatientService;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    IPatientService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPatientList() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(service.getAllPatientsPaginated(anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(patient)));

        // Act
        mockMvc.perform(get("/patient/list")
                        .param("page", "2"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getAllPatientsPaginated(1, 3);
    }

    @Test
    public void addPatientTest() throws Exception {
        mockMvc.perform(get("/patient/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addPatient"))
                .andExpect(content().string(containsString("Add New Patient")));
    }

    @Test
    public void addPatientPostTest() throws Exception {
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("familyName=TestNone&givenName=Test&dateOfBirth=1966-12-31&sex=F&address=1BrooksideSt&phone=100-222-3333")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).saveNewPatient(any(Patient.class));
    }

    @Test
    public void addPatientPostFormErrorTest() throws Exception {
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("familyName=TestNone&givenName=Test&dateOfBirth=1966-12-31&sex=F&address=1BrooksideSt&phone=")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addPatient"));
        verify(service, Mockito.never()).saveNewPatient(any(Patient.class));
    }

    @Test
    public void patientDetails() throws Exception {
        Patient patient = new Patient();
        when(service.getPatient(anyLong())).thenReturn(patient);
        mockMvc.perform(get("/patient/details/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("patientDetails"));
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void updatePatientForm() throws Exception {
        Patient patient = new Patient();
        when(service.getPatient(anyLong())).thenReturn(patient);
        mockMvc.perform(get("/patient/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updatePatient"))
                .andExpect(content().string(containsString("Update Patient")));
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void updatePatientFormNotFound() throws Exception {
        when(service.getPatient(anyLong())).thenThrow(PatientClientException.class);
        mockMvc.perform(get("/patient/update/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void updatePatientPostTest() throws Exception {
        mockMvc.perform(post("/patient/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("familyName=TestNone&givenName=Test&dateOfBirth=1966-12-31&sex=F&address=1BrooksideSt&phone=100-222-3333")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).updatePatient(any(Patient.class));
    }

    @Test
    public void updatePatientPostFormErrorTest() throws Exception {
        mockMvc.perform(post("/patient/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("familyName=TestNone&givenName=Test&dateOfBirth=1966-12-31&sex=F&address=1BrooksideSt&phone=")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updatePatient"));
        verify(service, Mockito.never()).updatePatient(any(Patient.class));
    }


    @Test
    public void deletePatientForm() throws Exception {
        Patient patient = new Patient();
        when(service.getPatient(anyLong())).thenReturn(patient);
        mockMvc.perform(get("/patient/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("deletePatient"))
                .andExpect(content().string(containsString("Delete Patient")));
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void deletePatientFormNotFound() throws Exception {
        when(service.getPatient(anyLong())).thenThrow(PatientClientException.class);
        mockMvc.perform(get("/patient/delete/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void deletePatientPostTest() throws Exception {
        mockMvc.perform(post("/patient/delete/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).deletePatient(1l);
    }

}
