package com.mediscreen.webapp.exception;


public class UnavailableNoteClientException extends ClientException {

    public UnavailableNoteClientException() {
        super("Unavailable note client exception.");
    }

    public UnavailableNoteClientException(String message) {
        super(message);
    }
}
