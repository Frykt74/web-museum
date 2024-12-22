package com.web.view.dto.equipment.models;

import java.time.Year;

public record EquipmentViewModel(
        Integer id,
        String name,
        String location,
        String category,
        String theme,
        Year year
) {
}
