package com.web.view.dto.location.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LocationCreateForm(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        Double ticketPrice
) {
}
