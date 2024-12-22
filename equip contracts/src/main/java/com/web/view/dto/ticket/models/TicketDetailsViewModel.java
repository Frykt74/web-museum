package com.web.view.dto.ticket.models;

import com.web.view.dto.base.BaseViewModel;

public record TicketDetailsViewModel(
        BaseViewModel base,
        TicketViewModel ticket
) {
}
