package com.museum.web.dtos.equipment;

import java.io.Serializable;

public record EquipmentNoteDto(
        Integer equipmentId,
        String equipmentName,
        String note,
        Integer noteId
) implements Serializable {
}
