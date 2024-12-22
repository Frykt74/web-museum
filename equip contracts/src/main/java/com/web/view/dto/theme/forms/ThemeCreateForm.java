package com.web.view.dto.theme.forms;

import jakarta.validation.constraints.NotBlank;

public record ThemeCreateForm(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Description cannot be empty")
        String description
) {
}
