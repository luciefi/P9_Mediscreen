package com.mediscreen.webapp.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Note {

    @Id
    private String id;

    private Long patientId;
    private Long userId;
    private String content;
    private Date creationDate;
    private Date modificationDate;

}
