package com.mediscreen.notes.service;

import com.mediscreen.notes.model.NoteEntity;
import org.springframework.data.domain.Page;

public interface INoteService {
    void saveNewNote(NoteEntity noteEntity);

    Page<NoteEntity> getAllNotesPaginated(long patientId, int pageNumber, int itemPerPage);

    NoteEntity getNote(String id);

    void updateNote(NoteEntity noteEntity);

    void deleteNote(String id);

    void deleteNotesForPatient(long patientId);
}
