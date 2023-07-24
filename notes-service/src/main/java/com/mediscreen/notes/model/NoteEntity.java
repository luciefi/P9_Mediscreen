package com.mediscreen.notes.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Document(collection = "notes")
public class NoteEntity {

    @Id
    private String id;

    @Indexed
    private Long patientId;
    private Long createdByUserId;
    private Long modifiedByUserId;
    private String content;
    private Date creationDate;
    private Date modificationDate;

}
