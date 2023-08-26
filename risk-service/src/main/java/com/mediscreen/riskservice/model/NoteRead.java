package com.mediscreen.riskservice.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class NoteRead {
    private String id;
    private Long patientId;
    private String content;
    private Date creationDate;
    private Date modificationDate;
}
