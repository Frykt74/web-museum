package com.museum.web.repositories;

import com.museum.web.entities.Category;
import com.museum.web.repositories.generics.ReadRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CategoryRepository extends
        ReadRepository<Category, Integer> {
}
