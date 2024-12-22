package com.museum.web.repositories;

import com.museum.web.entities.Visitor;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface VisitorRepository extends
        CreateRepository<Visitor, Integer>,
        ReadRepository<Visitor, Integer>,
        UpdateRepository<Visitor, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Visitor> findByEmail(String email);

    void markAsRemovedByEmail(String email);
}
