package com.mediscreen.webapp.integration.controller;

import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.exception.RiskClientException;
import com.mediscreen.webapp.exception.UnavailablePatientClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.RiskLevel;
import com.mediscreen.webapp.service.IPatientService;
import com.mediscreen.webapp.service.IRiskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class RiskControllerTest {

    @MockBean
    IRiskService service;

    @MockBean
    IPatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void getRiskLevel() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        when(service.getRisk(anyLong())).thenReturn(RiskLevel.NONE);

        // Act
        mockMvc.perform(get("/risk/1")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("risk"));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
        verify(service, Mockito.times(1)).getRisk(1l);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void getRiskLevelRiskClientException() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        when(service.getRisk(anyLong())).thenThrow(RiskClientException.class);

        // Act
        mockMvc.perform(get("/risk/1")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("riskUnavailable"));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
        verify(service, Mockito.times(1)).getRisk(1l);
    }


    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void getRiskPatientClientException() throws Exception {
        // Arrange
        when(patientService.getPatient(anyLong())).thenThrow(PatientClientException.class);

        // Act
        mockMvc.perform(get("/risk/1")).andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/patient/list"));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
        verify(service, Mockito.times(0)).getRisk(anyLong());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void getRiskWithPatientServiceUnavailable() throws Exception {
        // Arrange
        when(patientService.getPatient(anyLong())).thenThrow(UnavailablePatientClientException.class);

        // Act
        mockMvc.perform(get("/risk/1")).andDo(print()).andExpect(status().isOk()).andExpect(view().name("patientListUnavailable"));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
        verify(service, Mockito.times(0)).getRisk(1l);
    }
}
