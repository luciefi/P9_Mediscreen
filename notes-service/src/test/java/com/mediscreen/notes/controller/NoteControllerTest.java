package com.mediscreen.notes.controller;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.service.INoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @MockBean
    INoteService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getNoteById() throws Exception {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(service.getNote(anyString())).thenReturn(note);

        // Act
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getNote("1");

    }

    @Test
    void getPaginatedNotes() throws Exception {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(service.getAllNotesPaginated(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(note)));

        // Act
        mockMvc.perform(get("/notes/")
                        .param("patientId", "1")
                        .param("pageNumber", "1")
                        .param("itemPerPage", "3"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).getAllNotesPaginated(1, 1, 3);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidParams")
    void getPaginatedNotes_InvalidParam(String patientId, String pageNumber, String itemPerPage) throws Exception {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(service.getAllNotesPaginated(anyLong(), anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.singletonList(note)));

        // Act
        mockMvc.perform(get("/notes/")
                        .param("patientId", patientId)
                        .param("pageNumber", pageNumber)
                        .param("itemPerPage", itemPerPage))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.times(0)).getAllNotesPaginated(anyLong(), anyInt(), anyInt());
    }

    @Test
    void addNote() throws Exception {
        // Act
        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"patId\":\"1\",\n" +
                                "    \"content\":\"my_content\"\n" +
                                "}"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).saveNewNote(any(NoteEntity.class));
    }

    @Test
    void addNote_InvalidJson() throws Exception {
        // Act
        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"patId\":\"\",\n" +
                                "    \"content\":\"my_content\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.times(0)).saveNewNote(any(NoteEntity.class));
    }

    @Test
    void updateNote() throws Exception {
        // Act
        mockMvc.perform(put("/notes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\":\"abcde\",\n" +
                                "    \"content\":\"my_content\"\n" +
                                "}"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).updateNote(any(NoteEntity.class));
    }

    @Test
    void updateNote_InvalidJson() throws Exception {
        // Act
        mockMvc.perform(put("/notes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\":\"\",\n" +
                                "    \"content\":\"my_content\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());

        // Assert
        verify(service, Mockito.times(0)).updateNote(any(NoteEntity.class));
    }

    @Test
    void deleteNote() throws Exception {
        // Act
        mockMvc.perform(delete("/notes/abcde"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).deleteNote("abcde");
    }

    @Test
    void deleteNotesForPatient() throws Exception {
        // Act
        mockMvc.perform(delete("/notes/patient/123"))
                .andExpect(status().isOk());

        // Assert
        verify(service, Mockito.times(1)).deleteNotesForPatient(123);
    }

    @Test
    void handleNotFoundException() throws Exception {
        // Arrange
        doThrow(NoteNotFoundException.class).when(service).deleteNote(anyString());

        // Act
        mockMvc.perform(delete("/notes/abcde"))
                .andExpect(status().isNotFound());

        // Assert
        verify(service, Mockito.times(1)).deleteNote("abcde");
    }

    private static Stream<Arguments> provideInvalidParams() {
        return Stream.of(
                Arguments.of("", "1", "5"),
                Arguments.of("1", "-1", "5"),
                Arguments.of("1", "1", "-5")
        );
    }
}