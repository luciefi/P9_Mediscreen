package com.mediscreen.notes.service;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.repository.NoteRepository;
import com.mediscreen.notes.utils.UserUtils;
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
        noteEntity.setCreatedByUserId(UserUtils.getUserId());
        repository.insert(noteEntity);
    }

    @Override
    public Page<NoteEntity> getAllNotesPaginated(long patientId, int pageNumber, int itemPerPage) {
        return repository.findByPatientIdOrderByCreationDateDesc(patientId, PageRequest.of(pageNumber, itemPerPage));
    }

    @Override
    public NoteEntity getNote(String id) {
        return repository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    @Override
    public void updateNote(NoteEntity noteEntity) {

        noteEntity.setModificationDate(new Date());
        noteEntity.setModifiedByUserId(UserUtils.getUserId());

        NoteEntity oldNote = getNote(noteEntity.getId());
        noteEntity.setPatientId(oldNote.getPatientId());
        noteEntity.setCreationDate(oldNote.getCreationDate());
        noteEntity.setCreatedByUserId(oldNote.getCreatedByUserId());

        repository.save(noteEntity);
    }

    @Override
    public void deleteNote(String id) {
        getNote(id);
        repository.deleteById(id);
    }

    @Override
    public void deleteNotesForPatient(long patientId) {
        repository.deleteByPatientId(patientId);
    }
}
