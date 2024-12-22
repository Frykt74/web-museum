package com.web.view.dto.location.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LocationEditForm(
        @NotNull(message = "Id cannot be null")
        Integer id,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        Double ticketPrice
) {
}
