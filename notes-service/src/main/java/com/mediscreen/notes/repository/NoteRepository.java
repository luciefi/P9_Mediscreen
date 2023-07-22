package com.mediscreen.notes.repository;

import com.mediscreen.notes.model.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String> {
    Page<NoteEntity> findByPatientId(long patientId, PageRequest of);
}
