package com.mediscreen.webapp.exception;


public class NoteClientException extends ClientException {

    public NoteClientException() {
        super("Note client exception.");
    }

    public NoteClientException(String message) {
        super(message);
    }
}
