package com.web.view.dto.theme.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ThemeEditForm(
        @NotNull(message = "Id cannot be null")
        Integer id,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Description cannot be empty")
        String description
) {
}
