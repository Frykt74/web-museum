package com.museum.web.dtos.note;

import java.io.Serializable;

public record NoteDto(
        Integer id,
        Integer equipmentId,
        String fact
) implements Serializable {
}
