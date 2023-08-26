package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteClientFallbackTest {


    private NoteClientFallback noteClientFallback = new NoteClientFallback();

    @Test
    void findAll() {
        assertNull(noteClientFallback.findAll(1, 1, 1));
    }

    @Test
    void findById() {
        assertThrows(NoteClientException.class, () -> noteClientFallback.findById("123"));
    }

    @Test
    void createNote() {
        assertDoesNotThrow(() -> noteClientFallback.createNote(new NoteCreate()));
    }

    @Test
    void save() {
        NoteUpdate note = new NoteUpdate();
        note.setId("123");
        assertThrows(NoteClientException.class, () -> noteClientFallback.save(note));
    }

    @Test
    void deleteById() {
        assertThrows(NoteClientException.class, () -> noteClientFallback.deleteById("123"));
    }
}