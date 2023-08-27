package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteClientFallbackFactoryTest {


    private NoteClientFallbackFactory noteClientFallbackFactory = new NoteClientFallbackFactory();

    @Test
    void findAll() {
        assertNull(noteClientFallbackFactory.findAll(1, 1, 1));
    }

    @Test
    void findById() {
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.findById("123"));
    }

    @Test
    void createNote() {
        assertDoesNotThrow(() -> noteClientFallbackFactory.createNote(new NoteCreate()));
    }

    @Test
    void save() {
        NoteUpdate note = new NoteUpdate();
        note.setId("123");
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.save(note));
    }

    @Test
    void deleteById() {
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.deleteById("123"));
    }
}