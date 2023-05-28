package com.mediscreen.patientService.integration.controller;

import com.mediscreen.patientService.model.Patient;
import com.mediscreen.patientService.service.IPatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    public void addPatientTest() throws Exception {
        mockMvc.perform(get("/patient/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addPatient"))
                .andExpect(content().string(containsString("Add new patient")));
    }

    @Test
    public void createProfilePostTest() throws Exception {
        mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));
        verify(service, Mockito.times(1)).saveNewPatient(any(Patient.class));
    }

    @Test
    public void createProfilePostFormErrorTest() throws Exception {
        mockMvc.perform(post("/createProfile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addPatient"));
        verify(service, Mockito.never()).saveNewPatient(any(Patient.class));
    }

    @Test
    public void getPatientList() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("patientList"))
                .andExpect(content().string(containsString("Patient list")));
        verify(service, Mockito.times(1)).getAllPatients();
    }

}
