package com.mediscreen.notes.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteUpdate {
    @NotBlank
    private String id;

    @NotBlank
    private String content;
}
