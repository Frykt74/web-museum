package com.museum.web.dtos.equipment;

import java.io.Serializable;
import java.time.Year;
import java.util.Date;

public record EquipmentDto(
        Integer id,
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
) implements Serializable {
}
