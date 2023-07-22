package com.mediscreen.notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class NoteCreate {
    private Long patientId;
    private String content;
}
