package com.museum.web.repositories.impl;

import com.museum.web.entities.Ticket;
import com.museum.web.repositories.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TicketRepositoryImpl extends CustomCrudRepositoryImpl<Ticket, Integer> implements TicketRepository {
    @PersistenceContext
    private EntityManager entityManager;

    protected TicketRepositoryImpl() {
        super(Ticket.class);
    }

    @Override
    public Map<Integer, Long> countTicketsByLocationIds(List<Integer> locationIds) {
        String jpql = """
                     SELECT t.location.id, COUNT(t)\s
                     FROM Ticket t\s
                     WHERE t.location.id IN :locationIds\s
                     GROUP BY t.location.id
                \s""";
        List<Object[]> results = entityManager.createQuery(jpql, Object[].class)
                .setParameter("locationIds", locationIds)
                .getResultList();

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public Page<Ticket> findTicketsByVisitorId(Integer visitorId, Pageable pageable) {
        String jpql = "SELECT t FROM Ticket t WHERE t.visitor.id = :visitorId";
        TypedQuery<Ticket> tickets = entityManager.createQuery(jpql, Ticket.class);
        tickets.setParameter("visitorId", visitorId);
        tickets.setFirstResult((int) pageable.getOffset());
        tickets.setMaxResults(pageable.getPageSize());
        List<Ticket> ticketList = tickets.getResultList();

        String countTickets = "SELECT COUNT(t) FROM Ticket t WHERE t.visitor.id = :visitorId";
        TypedQuery<Long> countTicket = entityManager.createQuery(countTickets, Long.class);
        countTicket.setParameter("visitorId", visitorId);
        Long count = countTicket.getSingleResult();

        return new PageImpl<>(ticketList, pageable, count);
    }

    @Override
    public void markAsPaid(Integer ticketId) {
        String jpql = "UPDATE Ticket t SET t.isPaid = true WHERE t.id = :ticketId";
        entityManager.createQuery(jpql)
                .setParameter("ticketId", ticketId)
                .executeUpdate();
    }
}
