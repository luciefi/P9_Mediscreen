package com.mediscreen.notes.model;

import lombok.Data;

import java.util.Date;

@Data
public class NoteRead {
    private String id;
    private Long patientId;
    private String content;
    private Date creationDate;
    private Date modificationDate;
}
