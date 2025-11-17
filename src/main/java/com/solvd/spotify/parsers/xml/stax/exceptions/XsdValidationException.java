package com.solvd.spotify.parsers.xml.stax.exceptions;

public class XsdValidationException extends RuntimeException {

    public XsdValidationException(String message) {
        super(message);
    }

    public XsdValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
