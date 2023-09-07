package com.mediscreen.riskservice.controller;

import com.mediscreen.riskservice.exception.ClientException;
import com.mediscreen.riskservice.exception.PatientClientException;
import com.mediscreen.riskservice.exception.PatientNotFoundException;
import com.mediscreen.riskservice.model.RiskLevel;
import com.mediscreen.riskservice.service.IRiskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RiskControllerTest {

    @MockBean
    IRiskService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPatientById() throws Exception {
        // Arrange
        when(service.getPatientRisk(anyLong())).thenReturn(RiskLevel.NONE);

        // Act
        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getPatientRisk(1l);
    }

    @Test
    void getPatientById_InvalidId() throws Exception {
        // Act
        mockMvc.perform(get("/risk/-1"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.times(0)).getPatientRisk(anyLong());
    }

    @Test
    void getPatientById_ClientException() throws Exception {
        // Arrange
        when(service.getPatientRisk(anyLong())).thenThrow(ClientException.class);

        // Act
        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isInternalServerError());

        // Assert
        verify(service, Mockito.times(1)).getPatientRisk(1l);
    }

    @Test
    void getPatientById_NotFoundException() throws Exception {
        // Arrange
        when(service.getPatientRisk(anyLong())).thenThrow(PatientNotFoundException.class);

        // Act
        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isNotFound());

        // Assert
        verify(service, Mockito.times(1)).getPatientRisk(1l);
    }


}