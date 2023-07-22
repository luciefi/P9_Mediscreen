package com.mediscreen.notes.exception;


public class NoteNotFoundException extends NotFoundException {
    public NoteNotFoundException() {
        super("Note could not be found");
    }
}
