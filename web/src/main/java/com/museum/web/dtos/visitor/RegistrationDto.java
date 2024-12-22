package com.museum.web.dtos.visitor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
        @NotEmpty(message = "Surname cannot be null or empty!")
        @Size(min = 2, max = 50)
        String surname,

        @NotEmpty(message = "Firstname cannot be null or empty!")
        @Size(min = 2, max = 50)
        String firstname,

        String patronymic,

        @NotEmpty(message = "Email cannot be null or empty!")
        @Email(message = "Invalid email format!")
        String email,

        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Введите корректный номер телефона")
        String phone,

        @NotEmpty(message = "Password cannot be null or empty!")
        @Size(min = 4, message = "Password must be at least 4 characters!")
        String password,

        @NotEmpty(message = "Confirm password cannot be null or empty!")
        String confirmPassword
) {
}
