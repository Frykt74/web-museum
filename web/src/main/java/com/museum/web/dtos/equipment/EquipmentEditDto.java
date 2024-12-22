package com.museum.web.dtos.equipment;

import java.time.Year;

public record EquipmentEditDto(
        Integer id,
        Integer categoryId,
        Integer themeId,
        Integer locationId,
        String name,
        Year year,
        Integer country,
        String description,
        String image
) {
}
