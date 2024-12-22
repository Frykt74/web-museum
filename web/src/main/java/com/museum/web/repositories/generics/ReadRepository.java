package com.museum.web.repositories.generics;

import com.museum.web.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadRepository<T extends BaseEntity, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);
}
