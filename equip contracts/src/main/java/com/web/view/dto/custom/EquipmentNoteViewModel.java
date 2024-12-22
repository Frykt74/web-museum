package com.web.view.dto.custom;

public record EquipmentNoteViewModel(
        Integer equipmentId,
        String equipmentName,
        String note,
        Integer noteId
) {
}
