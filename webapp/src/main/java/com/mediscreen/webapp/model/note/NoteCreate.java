package com.mediscreen.webapp.model.note;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NoteCreate {

    @Positive
    @JsonProperty("patId")
    private long patientId;

    @NotBlank(message = "The note cannot be blank.")
    private String content;

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
