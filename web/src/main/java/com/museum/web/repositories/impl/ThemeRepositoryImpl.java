package com.museum.web.repositories.impl;

import com.museum.web.entities.Theme;
import com.museum.web.repositories.ThemeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepositoryImpl extends CustomCrudRepositoryImpl<Theme, Integer> implements ThemeRepository {
    protected ThemeRepositoryImpl() {
        super(Theme.class);
    }
}
