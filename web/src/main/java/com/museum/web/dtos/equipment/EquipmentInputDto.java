package com.museum.web.dtos.equipment;

import java.time.Year;
import java.util.Date;

public record EquipmentInputDto(
        Integer categoryId,
        Integer themeId,
        Integer locationId,
        String name,
        Year year,
        Integer country,
        String description,
        String image,
        Date date,
        String note
) {
}
