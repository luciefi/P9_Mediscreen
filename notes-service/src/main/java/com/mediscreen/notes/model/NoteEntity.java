package com.mediscreen.notes.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Document(collection = "notes")
public class NoteEntity { // TODO créer model DTO (create), model entity, model create, model update, userId récupérer

    @Id
    private String id;

    @Indexed
    private Long patientId;
    private Long userId;
    private String content;
    private Date creationDate;
    private Date modificationDate;

}
