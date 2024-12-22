package com.museum.web.repositories.impl;

import com.museum.web.entities.Role;
import com.museum.web.repositories.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl extends CustomCrudRepositoryImpl<Role, Integer> implements RoleRepository {
    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByValue(int value) {
        return entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.id = :value", Role.class)
                .setParameter("value", value)
                .getResultStream()
                .findFirst();
    }
}
