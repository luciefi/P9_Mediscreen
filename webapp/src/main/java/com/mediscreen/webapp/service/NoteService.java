package com.mediscreen.webapp.service;

import com.mediscreen.webapp.client.NoteClient;
import com.mediscreen.webapp.model.note.NoteCreate;
import com.mediscreen.webapp.model.note.NoteRead;
import com.mediscreen.webapp.model.note.NoteUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteClient client;

    @Override
    public void saveNewNote(NoteCreate note) {
        client.createNote(note);
    }

    @Override
    public Page<NoteRead> getAllNotesPaginated(long patientId, int pageNumber, int itemPerPage) {
        return client.findAll(patientId, pageNumber, itemPerPage);
    }

    @Override
    public NoteRead getNote(String id) {
        return client.findById(id);
    }

    @Override
    public void updateNote(NoteRead note) {
        client.save(getNoteUpdate(note));
    }

    @Override
    public void deleteNote(String id) {
        client.deleteById(id);
    }

    @Override
    public NoteUpdate getNoteUpdate(NoteRead note) {
        NoteUpdate noteUpdate = new NoteUpdate();
        noteUpdate.setContent(note.getContent());
        noteUpdate.setId(note.getId());
        return noteUpdate;
    }
}
