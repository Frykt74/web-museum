package com.museum.web.repositories.impl;

import com.museum.web.entities.Location;
import com.museum.web.repositories.LocationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryImpl extends CustomCrudRepositoryImpl<Location, Integer> implements LocationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected LocationRepositoryImpl() {
        super(Location.class);
    }

    public long countVisitsByLocationId(Integer locationId) {
        return entityManager.createQuery(
                        "SELECT COUNT(t) FROM Ticket t WHERE t.location.id = :locationId", Long.class)
                .setParameter("locationId", locationId)
                .getSingleResult();
    }
}
