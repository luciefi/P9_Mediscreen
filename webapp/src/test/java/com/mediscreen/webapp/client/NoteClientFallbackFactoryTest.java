package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.exception.UnavailableNoteClientException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteUpdate;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class NoteClientFallbackFactoryTest {


    private NoteClientFallbackFactory noteClientFallbackFactory = new NoteClientFallbackFactory();

    @Test
    void findAll() {
        NoteClientException exception = assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(mock(FeignException.BadRequest.class)).findAll(1, 1, 1));
        assertEquals("\u26a0 \u2007 Error while retrieving note list. (Bad request)", exception.getMessage());
    }

    @Test
    void findById() {
        NoteClientException exception = assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(mock(FeignException.NotFound.class)).findById("123"));
        assertEquals("\u26a0 \u2007 Error while retrieving note with id: 123. (Not found)", exception.getMessage());
    }

    @Test
    void createNote() {
        UnavailableNoteClientException exception = assertThrows(UnavailableNoteClientException.class, () -> noteClientFallbackFactory.create(mock(RetryableException.class)).createNote(new NoteCreate()));
        assertEquals("\u26a0 \u2007 Error while saving new note with content: null. Service is unavailable.", exception.getMessage());
    }

    @Test
    void save() {
        NoteUpdate note = new NoteUpdate();
        note.setId("123");
        NoteClientException exception = assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).save(note));
        assertEquals("\u26a0 \u2007 Error while updating note with content: null with id: 123.", exception.getMessage());
    }

    @Test
    void deleteById() {
        NoteClientException exception = assertThrows(NoteClientException.class, () -> noteClientFallbackFactory.create(new Throwable()).deleteById("123"));
        assertEquals("\u26a0 \u2007 Error while deleting note with id: 123.", exception.getMessage());
    }
}