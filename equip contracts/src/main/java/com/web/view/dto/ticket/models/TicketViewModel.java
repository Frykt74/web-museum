package com.web.view.dto.ticket.models;

import java.util.Date;

public record TicketViewModel(
        Integer id,
        String visitor,
        String location,
        Date date,
        Boolean isPaid,
        Integer visitorId
) {
}
