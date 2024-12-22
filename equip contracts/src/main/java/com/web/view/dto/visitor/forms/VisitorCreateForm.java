package com.web.view.dto.visitor.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VisitorCreateForm(
        @NotBlank(message = "Surname cannot be empty")
        String surname,

        @NotBlank(message = "Firstname cannot be empty")
        String firstname,

        @NotBlank(message = "Patronymic cannot be empty")
        String patronymic,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Введите корректный email")
        String email,

        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Введите корректный номер телефона")
        String phone
) {
}
