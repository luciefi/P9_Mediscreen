package com.mediscreen.webapp.model.note;


import javax.validation.constraints.NotBlank;

public class NoteUpdate {
    @NotBlank
    private String id;

    @NotBlank(message = "The note cannot be blank.")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
