package com.museum.web.repositories;

import com.museum.web.entities.Ticket;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.ReadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface TicketRepository extends
        CreateRepository<Ticket, Integer>,
        ReadRepository<Ticket, Integer> {

    Map<Integer, Long> countTicketsByLocationIds(List<Integer> locationIds);

    Page<Ticket> findTicketsByVisitorId(Integer visitorId, Pageable pageable);

    void markAsPaid(Integer ticketId);
}
