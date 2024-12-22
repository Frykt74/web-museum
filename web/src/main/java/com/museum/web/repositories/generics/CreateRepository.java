package com.museum.web.repositories.generics;

import com.museum.web.entities.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CreateRepository<T extends BaseEntity, ID> {
    <S extends T> S save(S entity);
}
