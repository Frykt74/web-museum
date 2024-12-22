package com.museum.web.entities.enums;

import java.util.Arrays;

public enum Country {
    RUSSIA(1),
    SOVIET_UNION(2),
    USA(3),
    GERMANY(4);

    private final int id;

    Country(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Country fromId(int id) {
        return Arrays.stream(Country.values())
                .filter(country -> country.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
    }
}
