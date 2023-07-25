package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.NoteClient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class NoteServiceTest {

    @Mock
    private NoteClient client;

    @InjectMocks
    private NoteService service;

    @Test
    void saveNewNote() {
        // Arrange
        NoteCreate note = new NoteCreate();

        // Act
        service.saveNewNote(note);

        // Assert
        verify(client, times(1)).createNote(any(NoteCreate.class));
    }

    @Test
    void getAllNotesPaginated() {
        // Act
        Page<NoteRead> notes = service.getAllNotesPaginated(1, 0, 2);

        // Assert
        verify(client, times(1)).findAll(1, 0, 2);
    }

    @Test
    void getNote() {
        // Arrange
        NoteRead note = new NoteRead();
        when(client.findById(anyString())).thenReturn(note);

        // Act
        service.getNote("abc");

        // Assert
        verify(client, times(1)).findById("abc");
    }

    @Test
    void updateNote() {
        // Arrange
        NoteUpdate note = new NoteUpdate();

        // Act
        service.updateNote(note);

        // Assert
        verify(client, times(1)).save(any(NoteUpdate.class));
    }

    @Test
    void deleteNotes() {
        // Act
        service.deleteNote("abc");

        // Assert
        verify(client, times(1)).deleteById("abc");
    }

}
