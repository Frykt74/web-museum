package com.museum.web.dtos.location;

public record LocationEditDto(
        Integer id,
        String name,
        Double ticketPrice
) {
}
