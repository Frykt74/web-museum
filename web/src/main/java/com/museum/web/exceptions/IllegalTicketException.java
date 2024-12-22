package com.museum.web.exceptions;

public class IllegalTicketException extends ValidationException {
    public IllegalTicketException(String message) {
        super(message);
    }
}
