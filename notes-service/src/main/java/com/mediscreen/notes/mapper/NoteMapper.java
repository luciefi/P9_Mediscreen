package com.mediscreen.notes.mapper;

import com.mediscreen.notes.model.NoteCreate;
import com.mediscreen.notes.model.NoteEntity;
import com.mediscreen.notes.model.NoteRead;
import com.mediscreen.notes.model.NoteUpdate;

public class NoteMapper {
    public static NoteRead convertToNoteRead(NoteEntity note) {
        NoteRead noteRead = new NoteRead();
        noteRead.setId(note.getId());
        noteRead.setPatientId(note.getPatientId());
        noteRead.setContent(note.getContent());
        noteRead.setCreationDate(note.getCreationDate());
        noteRead.setModificationDate(note.getModificationDate());
        return noteRead;
    }

    public static NoteEntity convertToNoteEntity(NoteCreate note) {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setPatientId(note.getPatientId());
        noteEntity.setContent(note.getContent());
        return noteEntity;
    }

    public static NoteEntity convertToNoteEntity(NoteUpdate note) {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(note.getId());
        noteEntity.setContent(note.getContent());
        return noteEntity;
    }
}
