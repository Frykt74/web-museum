package com.museum.web.dtos.visitor;

public record VisitorInputDto(
        String surname,
        String firstname,
        String patronymic,
        String email,
        String phone
) {
}
