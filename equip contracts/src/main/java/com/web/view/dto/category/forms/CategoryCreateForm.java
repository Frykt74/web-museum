package com.web.view.dto.category.forms;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateForm(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Description cannot be empty")
        String description
) {
}
