package com.web.view.dto.visitor.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VisitorEditForm(
        @NotBlank(message = "Surname cannot be empty")
        String surname,

        @NotBlank(message = "Surname cannot be empty")
        String firstname,

        @NotBlank(message = "Surname cannot be empty")
        String patronymic,

        @NotBlank(message = "Surname cannot be empty")
        @Email(message = "Введите корректный email")
        String email,

        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Введите корректный номер телефона")
        String phone
) {
}
