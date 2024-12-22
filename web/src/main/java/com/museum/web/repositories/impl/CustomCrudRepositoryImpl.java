package com.museum.web.repositories.impl;

import com.museum.web.entities.BaseEntity;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.DeleteRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@Transactional
public abstract class CustomCrudRepositoryImpl<T extends BaseEntity, ID> implements
        CreateRepository<T, ID>, ReadRepository<T, ID>,
        UpdateRepository<T, ID>, DeleteRepository<T, ID> {

    private final Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    protected CustomCrudRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void delete(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }

    @Override
    public boolean deleteById(ID id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        String query = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(query, entityClass).getResultList();
    }

    public Page<T> findAll(Pageable pageable) {
        String query = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> typedQuery = entityManager.createQuery(query, entityClass);

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<T> results = typedQuery.getResultList();

        String countQuery = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery, Long.class);
        Long total = countTypedQuery.getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }


    @Override
    public <S extends T> S update(S entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
}
