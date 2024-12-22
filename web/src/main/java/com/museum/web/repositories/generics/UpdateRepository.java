package com.museum.web.repositories.generics;

import com.museum.web.entities.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UpdateRepository<T extends BaseEntity, ID> {
    <S extends T> S update(S entity);
}
