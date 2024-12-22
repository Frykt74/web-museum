package com.museum.web.repositories.impl;

import com.museum.web.entities.Category;
import com.museum.web.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl extends CustomCrudRepositoryImpl<Category, Integer> implements CategoryRepository {
    protected CategoryRepositoryImpl() {
        super(Category.class);
    }
}
