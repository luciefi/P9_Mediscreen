package com.mediscreen.webapp.exception;

public class LoginNotFoundException extends IllegalArgumentException {
    public LoginNotFoundException() {
        super("User name not found");
    }
}
