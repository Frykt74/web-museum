package com.museum.web.repositories.impl;

import com.museum.web.entities.Visitor;
import com.museum.web.repositories.VisitorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class VisitorRepositoryImpl extends CustomCrudRepositoryImpl<Visitor, Integer> implements VisitorRepository {
    @PersistenceContext
    private EntityManager entityManager;

    protected VisitorRepositoryImpl() {
        super(Visitor.class);
    }

    @Override
    public boolean existsByEmail(String email) {
        String hql = "select count(*) from Visitor v where v.email = :email and isRemoved = false";
        long count = (Long) entityManager.createQuery(hql).setParameter("email", email).getSingleResult();
        return (count > 0);
    }

    @Override
    public boolean existsByPhone(String phone) {
        String hql = "select count(*) from Visitor v where v.phone = :phone and isRemoved = false";
        long count = (Long) entityManager.createQuery(hql).setParameter("phone", phone).getSingleResult();
        return (count > 0);
    }

    @Override
    public Optional<Visitor> findByEmail(String email) {
        String hql = "select v from Visitor v where v.email = :email and isRemoved = false";
        try {
            Visitor visitor = (Visitor) entityManager.createQuery(hql).setParameter("email", email).getSingleResult();
            return Optional.of(visitor);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void markAsRemovedByEmail(String email) {
        String hql = "update Visitor v set isRemoved = true where v.email = :email";
        entityManager.createQuery(hql).setParameter("email", email).executeUpdate();
    }
}
