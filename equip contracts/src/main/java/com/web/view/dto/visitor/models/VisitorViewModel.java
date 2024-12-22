package com.web.view.dto.visitor.models;

public record VisitorViewModel(
        Integer id,
        String surname,
        String firstname,
        String patronymic,
        String email,
        String phone
) {
}
