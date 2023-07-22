package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository repository;

    @Override
    public void saveNewNote(NoteEntity noteEntity) {
        noteEntity.setCreationDate(new Date());
        repository.insert(noteEntity);
    }

    @Override
    public Page<NoteEntity> getAllNotesPaginated(long patientId, int pageNumber, int itemPerPage) {
        return repository.findByPatientId(patientId, PageRequest.of(pageNumber, itemPerPage));
    }

    @Override
    public NoteEntity getNote(String id) {
        return repository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    @Override
    public void updateNote(NoteEntity noteEntity) {
        noteEntity.setModificationDate(new Date());
        repository.save(noteEntity);
    }

    @Override
    public void deleteNote(String id) {
        getNote(id);
        repository.deleteById(id);
    }
}
