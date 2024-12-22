package com.museum.web.repositories.generics;

import com.museum.web.entities.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DeleteRepository<T extends BaseEntity, ID> {
    void delete(T entity);

    boolean deleteById(ID id);
}