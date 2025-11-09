package com.solvd.spotify.exceptions;

public class XsdValidationException extends RuntimeException {

    public XsdValidationException(String message) {
        super(message);
    }

    public XsdValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
