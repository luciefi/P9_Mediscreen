package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteClientException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class NoteClientFallback implements NoteClient {

    @Override
    public Page<NoteRead> findAll(long patientId, int pageNumber, int itemPerPage) {
        throwNoteClientException("Exception while retrieving note list from note service.");
        return null;
    }

    @Override
    public NoteRead findById(String id) {
        throwNoteClientException("Exception while retrieving note with id: " + id + " from note service.");
        return null;
    }

    @Override
    public void createNote(NoteCreate note) {
        throwNoteClientException("Exception while saving new note with content " + note.getContent() + " in note service.");
    }

    @Override
    public void save(NoteUpdate note) {
        throwNoteClientException("Exception while updating note with content " + note.getContent() + " with id: " + note.getId() + " in note service.");
    }

    @Override
    public void deleteById(String id) {
        throwNoteClientException("Exception while deleting note with id: " + id + " from note service.");
    }

    @Override
    public void deleteByPatientId(long id) {
        throwNoteClientException("Exception while deleting notes for patient with id: " + id + " from note service.");
    }

    private void throwNoteClientException(String message) {
        throw new NoteClientException(message);
    }
}
