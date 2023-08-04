package com.mediscreen.webapp.client;

import com.mediscreen.webapp.exception.NoteNotFoundException;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class NoteClientFallback implements NoteClient {

    Logger logger = LoggerFactory.getLogger(NoteClientFallback.class);

    @Override
    public Page<NoteRead> findAll(long patientId, int pageNumber, int itemPerPage) {
        return null;
    }

    @Override
    public NoteRead findById(String id) {
        throwNoteNotFound(id);
        return null;
    }

    @Override
    public void createNote(NoteCreate note) {
    }

    @Override
    public void save(NoteUpdate note) {
        throwNoteNotFound(note.getId());
    }

    @Override
    public void deleteById(String id) {
        throwNoteNotFound(id);
    }

    @Override
    public void deleteByPatientId(long id) {
    }

    private void throwNoteNotFound(String id) {
        logger.info("Note with id: " + id + " could not be found.");
        throw new NoteNotFoundException();
    }
}
