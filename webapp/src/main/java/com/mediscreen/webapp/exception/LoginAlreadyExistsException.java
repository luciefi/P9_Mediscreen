package com.mediscreen.webapp.exception;

public class LoginAlreadyExistsException extends IllegalArgumentException {
    public LoginAlreadyExistsException() {
        super("Login already exists, please find a different one.");
    }
}
