package com.museum.web.dtos.theme;

import java.io.Serializable;

public record ThemeDto(
        Integer id,
        String name,
        String description
) implements Serializable {
}
