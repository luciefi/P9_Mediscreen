package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith({MockitoExtension.class})
class NoteServiceTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private NoteService service;

    @Test
    void saveNewNote() {
        // Arrange
        NoteEntity note = new NoteEntity();

        // Act
        service.saveNewNote(note);

        // Assert
        verify(repository, times(1)).insert(note);
        assertNotNull(note.getCreationDate());
        assertNotNull(note.getCreatedByUserId());
    }

    @Test
    void getAllNotesPaginated() {
        // Act
        Page<NoteEntity> note = service.getAllNotesPaginated(1L, 0, 3);

        // Assert
        verify(repository, times(1)).findByPatientId(anyLong(), any(PageRequest.class));
    }

    @Test
    void getNote() {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(repository.findById(anyString())).thenReturn(Optional.of(note));

        // Act
        service.getNote("123");

        // Assert
        verify(repository, times(1)).findById("123");
    }

    @Test
    void getNote_NotFound() {
        // Arrange
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        assertThrows(NoteNotFoundException.class, () -> service.getNote("123"));

        // Assert
        verify(repository, times(1)).findById("123");
    }

    @Test
    void updateNote() {
        // Arrange
        NoteEntity note = new NoteEntity();
        note.setId("123");
        note.setCreationDate(new Date());
        note.setCreatedByUserId(1l);
        note.setPatientId(1l);
        when(repository.findById(anyString())).thenReturn(Optional.of(note));

        NoteEntity updatedNote = new NoteEntity();
        updatedNote.setId("123");

        // Act
        service.updateNote(updatedNote);

        // Assert
        verify(repository, times(1)).findById("123");
        verify(repository, times(1)).save(updatedNote);
        assertNotNull(updatedNote.getCreationDate());
        assertNotNull(updatedNote.getCreatedByUserId());
        assertNotNull(updatedNote.getModificationDate());
        assertNotNull(updatedNote.getModifiedByUserId());
        assertNotNull(updatedNote.getPatientId());
    }

    @Test
    void updateNote_NotFound() {
        // Arrange
        NoteEntity note = new NoteEntity();
        note.setId("123");
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        assertThrows(NoteNotFoundException.class, ()-> service.updateNote(note));

        // Assert
        verify(repository, times(1)).findById("123");
        verify(repository, times(0)).deleteById(anyString());
    }

    @Test
    void deleteNote() {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(repository.findById(anyString())).thenReturn(Optional.of(note));

        // Act
        service.deleteNote("123");

        // Assert
        verify(repository, times(1)).findById("123");
        verify(repository, times(1)).deleteById("123");
    }

    @Test
    void deleteNote_NotFound() {
        // Arrange
        NoteEntity note = new NoteEntity();
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        assertThrows(NoteNotFoundException.class, ()-> service.deleteNote("123"));

        // Assert
        verify(repository, times(1)).findById("123");
        verify(repository, times(0)).deleteById(anyString());
    }
}