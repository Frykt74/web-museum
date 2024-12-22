package com.museum.web.dtos.visitor;

public record VisitorDto(
        Integer id,
        String surname,
        String firstname,
        String patronymic,
        String email,
        String phone
) {
    public String fullNameWithInitials() {
        String initials = (firstname != null ? firstname.charAt(0) + "." : "") +
                (patronymic != null ? patronymic.charAt(0) + "." : "");
        return surname + " " + initials;
    }
}
