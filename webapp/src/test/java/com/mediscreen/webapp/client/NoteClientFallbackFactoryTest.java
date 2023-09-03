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
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).findAll(1, 1, 1));
    }

    @Test
    void findById() {
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).findById("123"));
    }

    @Test
    void createNote() {
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).createNote(new NoteCreate()));
    }

    @Test
    void save() {
        NoteUpdate note = new NoteUpdate();
        note.setId("123");
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).save(note));
    }

    @Test
    void deleteById() {
        assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).deleteById("123"));
    }
}