package com.mediscreen.webapp.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User could not be found");
    }
}
