package com.museum.web.exceptions;

public class ValidationException extends ServerException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super("Validation failed");
    }
}
