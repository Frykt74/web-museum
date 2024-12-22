package com.web.view.dto.equipment.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Year;
import java.util.Date;

public record EquipmentEditForm(
        @NotNull(message = "Id cannot be null")
        Integer id,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Description cannot be empty")
        String description,

        @NotNull(message = "Category ID cannot be null")
        Integer categoryId,

        @NotNull(message = "Theme ID cannot be null")
        Integer themeId,

        @NotNull(message = "Location ID cannot be null")
        Integer locationId,

        @NotNull(message = "Year cannot be null")
        Year year,

        @NotNull(message = "Country ID cannot be null")
        Integer countryId,

        @NotBlank(message = "Image cannot be empty")
        String image
) {
}
