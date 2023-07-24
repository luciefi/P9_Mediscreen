package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.springframework.data.domain.Page;

public interface INoteService {
    void saveNewNote(NoteCreate note);

    Page<NoteRead> getAllNotesPaginated(int pageNumber, int itemPerPage);

    NoteRead getNote(long l);

    void updateNote(NoteUpdate note);

    void deleteNote(long id);
}
