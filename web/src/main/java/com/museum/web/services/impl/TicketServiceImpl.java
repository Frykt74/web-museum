package com.museum.web.services.impl;

import com.museum.web.dtos.ticket.TicketDto;
import com.museum.web.dtos.ticket.TicketInputDto;
import com.museum.web.entities.Location;
import com.museum.web.entities.Ticket;
import com.museum.web.entities.Visitor;
import com.museum.web.exceptions.LocationNotFoundException;
import com.museum.web.exceptions.TicketNotFoundException;
import com.museum.web.exceptions.VisitorNotFoundException;
import com.museum.web.repositories.LocationRepository;
import com.museum.web.repositories.TicketRepository;
import com.museum.web.repositories.VisitorRepository;
import com.museum.web.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final VisitorRepository visitorRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             VisitorRepository visitorRepository,
                             LocationRepository locationRepository) {
        this.ticketRepository = ticketRepository;
        this.visitorRepository = visitorRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public TicketDto findById(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException((id)));
        return toDto(ticket);
    }

    @Override
    public Page<TicketDto> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public void addTicket(TicketInputDto ticketInputDto) {
        Ticket ticket = toEntity(ticketInputDto);
        ticketRepository.save(ticket);
    }

    @Override
    public Page<TicketDto> findUnpaidTickets(Integer visitorId, Pageable pageable) {
        Page<Ticket> visitorTickets = ticketRepository.findTicketsByVisitorId(visitorId, pageable);
        List<TicketDto> filteredTickets = visitorTickets.stream()
                .filter(ticket -> !ticket.getIsPaid())
                .map(this::toDto)
                .toList();

        return new PageImpl<>(filteredTickets, pageable, visitorTickets.getTotalElements());
    }

    @Override
    public Page<TicketDto> findPaidTickets(Integer visitorId, Pageable pageable) {
        Page<Ticket> visitorTickets = ticketRepository.findTicketsByVisitorId(visitorId, pageable);
        List<TicketDto> filteredTickets = visitorTickets.stream()
                .filter(Ticket::getIsPaid)
                .map(this::toDto)
                .toList();

        return new PageImpl<>(filteredTickets, pageable, visitorTickets.getTotalElements());
    }

    @Override
    public void getPayment(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException((ticketId)));

        ticketRepository.markAsPaid(ticketId);
    }

    @Override
    public void addTicketToCart(Integer visitorId, Integer locationId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new VisitorNotFoundException((visitorId)));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException((locationId)));

        Ticket ticket = new Ticket(visitor, location, new Date(), false);
        ticketRepository.save(ticket);
    }

    private TicketDto toDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getVisitor().getId(),
                ticket.getLocation().getId(),
                ticket.getDate(),
                ticket.getIsPaid()
        );
    }

    private Ticket toEntity(TicketInputDto dto) {
        Visitor visitor = visitorRepository.findById(dto.visitorId())
                .orElseThrow(() -> new VisitorNotFoundException(dto.visitorId()));
        Location location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new LocationNotFoundException(dto.locationId()));

        return new Ticket(visitor, location, dto.date(), false);
    }
}

