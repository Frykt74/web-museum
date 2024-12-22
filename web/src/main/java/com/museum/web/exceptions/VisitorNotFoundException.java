package com.museum.web.exceptions;

public class VisitorNotFoundException extends ResourceNotFoundException {
    public VisitorNotFoundException(String message) {
        super(message);
    }

    public VisitorNotFoundException() {
        super("Visitor not found");
    }

    public VisitorNotFoundException(Integer id) {
        super("Visitor not found with id " + id);
    }
}
