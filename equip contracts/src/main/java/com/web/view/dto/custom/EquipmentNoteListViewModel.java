package com.web.view.dto.custom;

import java.util.List;

public record EquipmentNoteListViewModel(
        String title,
        List<EquipmentNoteViewModel> equipmentNotes,
        Integer totalPages
) {
}
