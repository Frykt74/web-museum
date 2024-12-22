package com.museum.web.repositories;

import com.museum.web.entities.Theme;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface ThemeRepository extends
        CreateRepository<Theme, Integer>,
        ReadRepository<Theme, Integer>,
        UpdateRepository<Theme, Integer> {
}
