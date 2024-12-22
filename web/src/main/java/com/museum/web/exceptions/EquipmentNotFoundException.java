package com.museum.web.exceptions;

public class EquipmentNotFoundException extends ResourceNotFoundException {
    public EquipmentNotFoundException(String message) {
        super(message);
    }

    public EquipmentNotFoundException() {
        super("Equipment not found");
    }

    public EquipmentNotFoundException(int id) {
        super("Equipment with id " + id + " not found");
    }
}
