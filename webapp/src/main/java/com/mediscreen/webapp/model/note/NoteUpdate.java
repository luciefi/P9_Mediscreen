package com.mediscreen.webapp.model.note;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteUpdate {
    @NotBlank
    private String id;

    @NotBlank
    private String content;
}
