package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.springframework.data.domain.Page;

public class NoteService implements INoteService {
    @Override
    public void saveNewNote(NoteCreate note) {

    }

    @Override
    public Page<NoteRead> getAllNotesPaginated(int pageNumber, int itemPerPage) {
        return null;
    }

    @Override
    public NoteRead getNote(long l) {
        return null;
    }

    @Override
    public void updateNote(NoteUpdate note) {

    }

    @Override
    public void deleteNote(long id) {

    }
}
