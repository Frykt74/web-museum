package com.museum.web.exceptions;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
        super("Category not found");
    }

    public CategoryNotFoundException(int id) {
        super("Category with id " + id + " not found");
    }
}
