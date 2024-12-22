package com.museum.web.repositories.impl;

import com.museum.web.entities.Equipment;
import com.museum.web.repositories.EquipmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EquipmentRepositoryImpl extends CustomCrudRepositoryImpl<Equipment, Integer> implements EquipmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    protected EquipmentRepositoryImpl() {
        super(Equipment.class);
    }

    @Override
    public List<Equipment> findAllByThemeId(int themeId) {
        String jpql = "SELECT eq FROM Equipment eq WHERE eq.theme.id = :themeId";
        return entityManager.createQuery(jpql, Equipment.class)
                .setParameter("themeId", themeId)
                .getResultList();
    }

    @Override
    public List<Equipment> findAllByCategoryIdWithNotesFirst(int categoryId) {
        String jpql = """
                    SELECT eq FROM Equipment eq
                    LEFT JOIN fetch Note n ON n.equipment.id = eq.id
                    WHERE eq.category.id = :categoryId
                    ORDER BY CASE WHEN n.fact IS NULL THEN 1 ELSE 0 END, n.fact DESC
                """;
        return entityManager.createQuery(jpql, Equipment.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public Page<Object[]> findAllEquipmentWithOrWithoutNotes(Pageable pageable) {
        String jpql = """
                 SELECT e, n\s
                 FROM Equipment e\s
                 LEFT JOIN Note n ON e = n.equipment
                \s""";

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Object[]> results = query.getResultList();

        String countJpql = "SELECT COUNT(e) FROM Equipment e";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }
}
