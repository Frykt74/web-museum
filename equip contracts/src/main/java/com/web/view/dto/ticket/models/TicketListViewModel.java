package com.web.view.dto.ticket.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record TicketListViewModel(
        BaseViewModel base,
        List<TicketViewModel> tickets,
        Integer totalPages
) {
}
