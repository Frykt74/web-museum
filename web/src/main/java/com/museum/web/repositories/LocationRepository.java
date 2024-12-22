package com.museum.web.repositories;

import com.museum.web.entities.Location;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.DeleteRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LocationRepository extends
        CreateRepository<Location, Integer>,
        ReadRepository<Location, Integer>,
        UpdateRepository<Location, Integer> {
}
