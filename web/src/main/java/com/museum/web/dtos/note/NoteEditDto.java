package com.museum.web.dtos.note;

public record NoteEditDto(
        Integer id,
        Integer equipmentId,
        String fact
) {
}
