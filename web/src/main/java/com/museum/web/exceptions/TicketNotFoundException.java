package com.museum.web.exceptions;

public class TicketNotFoundException extends ResourceNotFoundException {
    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException() {
        super("Ticket not found");
    }

    public TicketNotFoundException(Integer id) {
        super("Ticket with id " + id + " not found");
    }
}
