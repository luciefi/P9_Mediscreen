package com.mediscreen.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class NoteCreate {
    @Positive
    @JsonProperty("patId")
    private long patientId;

    @NotBlank
    private String content;
}
