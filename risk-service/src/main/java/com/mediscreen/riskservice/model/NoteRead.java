package com.mediscreen.riskservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NoteRead {
    private String id;
    private Long patientId;
    private String content;
    private Date creationDate;
    private Date modificationDate;
}
