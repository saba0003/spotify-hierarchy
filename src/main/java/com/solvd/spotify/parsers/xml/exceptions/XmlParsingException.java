package com.solvd.spotify.parsers.xml.exceptions;

public class XmlParsingException extends RuntimeException {

    private static final String PREFIX = "Error parsing XML: ";

    public XmlParsingException(String message) {
        super(PREFIX + message);
    }

    public XmlParsingException(Throwable cause) {
        super(PREFIX + cause.getMessage());
    }

    public XmlParsingException(String message, Throwable cause) {
        super(PREFIX + message, cause);
    }
}
