package com.museum.web.dtos.category;

import java.io.Serializable;

public record CategoryDto(
        Integer id,
        String name,
        String description
) implements Serializable {
}
