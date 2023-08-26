package com.mediscreen.riskservice.exception;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(String s) {
        super(s);
    }
}