package com.museum.web.exceptions;

public class LocationNotFoundException extends ResourceNotFoundException {
    public LocationNotFoundException(String message) {
        super(message);
    }

    public LocationNotFoundException() {
        super("Location Not Found");
    }

    public LocationNotFoundException(int id) {
        super("Location with id " + id + " Not Found");
    }
}
