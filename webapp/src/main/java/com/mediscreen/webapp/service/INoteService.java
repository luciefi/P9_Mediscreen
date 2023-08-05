package com.mediscreen.webapp.service;

import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.springframework.data.domain.Page;

public interface INoteService {
    void saveNewNote(NoteCreate note);

    Page<NoteRead> getAllNotesPaginated(long patientId, int pageNumber, int itemPerPage);

    NoteRead getNote(String id);

    void updateNote(NoteRead note);

    void deleteNote(String id);

    NoteUpdate getNoteUpdate(NoteRead note);
}
