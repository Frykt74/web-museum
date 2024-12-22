package com.museum.web.dtos.location;

import java.io.Serializable;

public record LocationDto(
        Integer id,
        String name,
        Double ticketPrice
) implements Serializable {
}
