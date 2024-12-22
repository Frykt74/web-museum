package com.web.view.dto.ticket.forms;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record TicketCreateForm(
        @NotNull(message = "Visitor ID cannot be null")
        Integer visitorId,

        @NotNull(message = "Location ID cannot be null")
        Integer locationId,

        @NotNull(message = "Visit date cannot be null")
        @FutureOrPresent(message = "Visit date must be today or in the future")
        Date visitDate,

        @NotNull(message = "Payment status cannot be null")
        Boolean isPaid
) {
}
