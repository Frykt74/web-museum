package com.museum.web.dtos.ticket;

import java.util.Date;

public record TicketDto(
        Integer id,
        Integer visitorId,
        Integer locationId,
        Date date,
        Boolean isPaid
) {
}
