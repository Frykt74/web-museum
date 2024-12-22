package com.museum.web.dtos.visitor;

public record VisitorEditDto(
        String surname,
        String firstname,
        String patronymic,
        String email,
        String phone
) {
}
