package com.museum.web.exceptions;

public class NoteNotFoundException extends ResourceNotFoundException {
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException() {
        super("Note Not Found");
    }

    public NoteNotFoundException(Integer id) {
        super("Note with id " + id + " not found");
    }
}
