package com.mediscreen.webapp.integration.controller;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.exception.PatientClientException;
import com.mediscreen.webapp.model.Patient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.service.INoteService;
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
public class NoteControllerTest {

    @MockBean
    IPatientService patientService;

    @MockBean
    INoteService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getNoteList() throws Exception {
        // Arrange
        NoteRead note = new NoteRead();
        when(service.getAllNotesPaginated(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(note)));
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);

        // Act
        mockMvc.perform(get("/notes/1")
                        .param("page", "2"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getAllNotesPaginated(1l, 1, 3);
        verify(patientService, Mockito.times(1)).getPatient(1l);
    }

    @Test
    void getNoteList_PatientNotFound() throws Exception {
        // Arrange
        NoteRead note = new NoteRead();
        when(service.getAllNotesPaginated(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(note)));
        when(patientService.getPatient(anyLong())).thenThrow(PatientClientException.class);

        // Act
        mockMvc.perform(get("/notes/1")
                        .param("page", "2"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));

        // Assert
        verify(service, Mockito.times(0)).getAllNotesPaginated(anyLong(), anyInt(), anyInt());
        verify(patientService, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void addNoteTest() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);

        // Act
        mockMvc.perform(get("/notes/1/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addNote"))
                .andExpect(content().string(containsString("Add note for")));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void addNoteTest_PatientNotFound() throws Exception {
        // Arrange
        when(patientService.getPatient(anyLong())).thenThrow(PatientClientException.class);

        // Act
        mockMvc.perform(get("/notes/1/add"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/patient/list"));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void addNotePostTest() throws Exception {
        mockMvc.perform(post("/notes/2/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("content=note&patId=1")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/2"));
        verify(service, Mockito.times(1)).saveNewNote(any(NoteCreate.class));
    }

    @Test
    public void addNotePostFormErrorTest() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);

        // Act
        mockMvc.perform(post("/notes/1/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("content=&patId=")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("addNote"));
        verify(service, Mockito.never()).saveNewNote(any(NoteCreate.class));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(1l);
    }

    @Test
    public void note() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        NoteRead note = new NoteRead();
        note.setPatientId(2l);
        when(service.getNote(anyString())).thenReturn(note);

        // Act
        mockMvc.perform(get("/notes/3/details/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("note"));
        // Assert
        verify(patientService, Mockito.times(1)).getPatient(2l);
        verify(service, Mockito.times(1)).getNote("1");

    }

    @Test
    public void note_NotFound() throws Exception {
        // Arrange
        when(service.getNote(anyString())).thenThrow(NoteClientException.class);

        // Act
        mockMvc.perform(get("/notes/3/details/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/3"));
        // Assert
        verify(service, Mockito.times(1)).getNote("1");

    }

    @Test
    public void updateNoteForm() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        NoteRead note = new NoteRead();
        note.setPatientId(2l);
        when(service.getNote(anyString())).thenReturn(note);

        // Act
        mockMvc.perform(get("/notes/3/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updateNote"))
                .andExpect(content().string(containsString("Update ")));

        // Assert
        verify(patientService, Mockito.times(1)).getPatient(2l);
        verify(service, Mockito.times(1)).getNote("1");
    }

    @Test
    public void updateNoteFormNotFound() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        when(service.getNote(anyString())).thenThrow(NoteClientException.class);


        // Act
        mockMvc.perform(get("/notes/2/update/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/2"));

        // Assert
        verify(service, Mockito.times(1)).getNote("1");
        verify(patientService, Mockito.times(0)).getPatient(2l);
    }

    @Test
    public void updateNotePostTest() throws Exception {
        mockMvc.perform(post("/notes/2/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("content=note&id=1")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/2"));
        verify(service, Mockito.times(1)).updateNote(any(NoteRead.class));
    }

    @Test
    public void updateNotePostFormErrorNoContentTest() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);

        // Act
        mockMvc.perform(post("/notes/2/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("content=&id=123&patientId=2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updateNote"));

        // Arrange
        verify(service, Mockito.never()).updateNote(any(NoteRead.class));
    }


    @Test
    public void deleteNoteForm() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        NoteRead note = new NoteRead();
        note.setPatientId(2l);
        when(service.getNote(anyString())).thenReturn(note);

        // Act
        mockMvc.perform(get("/notes/3/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("deleteNote"))
                .andExpect(content().string(containsString("Delete Note")));

        // Assert
        verify(service, Mockito.times(1)).getNote("1");
        verify(patientService, Mockito.times(1)).getPatient(2l);
    }

    @Test
    public void deleteNoteFormNotFound() throws Exception {
        // Arrange
        Patient patient = new Patient();
        when(patientService.getPatient(anyLong())).thenReturn(patient);
        when(service.getNote(anyString())).thenThrow(NoteClientException.class);

        // Act
        mockMvc.perform(get("/notes/2/delete/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/2"));

        // Assert
        verify(service, Mockito.times(1)).getNote("1");
    }

    @Test
    public void deleteNotePostTest() throws Exception {
        mockMvc.perform(post("/notes/2/delete/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/notes/2"));
        verify(service, Mockito.times(1)).deleteNote("1");
    }

}
