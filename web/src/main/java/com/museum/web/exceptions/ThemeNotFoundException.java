package com.museum.web.exceptions;

public class ThemeNotFoundException extends ResourceNotFoundException {
    public ThemeNotFoundException(String message) {
        super(message);
    }

    public ThemeNotFoundException() {
        super("Theme not found");
    }

    public ThemeNotFoundException(Integer id) {
        super("Theme with id " + id + " not found");
    }
}
