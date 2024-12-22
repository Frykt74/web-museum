package com.museum.web.services;

import com.museum.web.dtos.ticket.TicketDto;
import com.museum.web.dtos.ticket.TicketInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    TicketDto findById(Integer id);

    Page<TicketDto> findAll(Pageable pageable);

    void addTicket(TicketInputDto ticketInputDto);

    Page<TicketDto> findUnpaidTickets(Integer visitorId, Pageable pageable);

    void getPayment(Integer ticketId);

    Page<TicketDto> findPaidTickets(Integer visitorId, Pageable pageable);

    void addTicketToCart(Integer visitorId, Integer locationId);
}
