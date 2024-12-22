package com.museum.web.dtos.ticket;

import java.util.Date;

public record TicketInputDto(
        Integer visitorId,
        Integer locationId,
        Date date
) {
}
