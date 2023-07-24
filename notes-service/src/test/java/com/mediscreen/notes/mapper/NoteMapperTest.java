package com.mediscreen.notes.mapper;

import com.mediscreen.notes.model.NoteCreate;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.model.NoteRead;
import com.mediscreen.notes.model.NoteUpdate;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NoteMapperTest {

    @Test
    void convertToNoteRead() {
        // Arrange
        NoteEntity note = new NoteEntity();
        note.setId("id");
        note.setPatientId(1l);
        note.setContent("content");
        note.setCreationDate(new Date());
        note.setModificationDate(new Date());

        // Act
        NoteRead noteRead = NoteMapper.convertToNoteRead(note);

        // Assert
        assertEquals("id", noteRead.getId());
        assertEquals(1l, noteRead.getPatientId());
        assertEquals("content", noteRead.getContent());
        assertNotNull(noteRead.getCreationDate());
        assertNotNull(noteRead.getModificationDate());
    }

    @Test
    void convertToNoteEntity() {
        // Arrange
        NoteCreate noteCreate = new NoteCreate();
        noteCreate.setContent("content");
        noteCreate.setPatientId(1l);

        // Act
        NoteEntity note = NoteMapper.convertToNoteEntity(noteCreate);

        // Assert
        assertEquals("content", note.getContent());
        assertEquals(1l, note.getPatientId());
    }

    @Test
    void testConvertToNoteEntity() {
        // Arrange
        NoteUpdate noteUpdate = new NoteUpdate();
        noteUpdate.setContent("content");
        noteUpdate.setId("abc");

        // Act
        NoteEntity note = NoteMapper.convertToNoteEntity(noteUpdate);

        // Assert
        assertEquals("content", note.getContent());
        assertEquals("abc", note.getId());
    }
}