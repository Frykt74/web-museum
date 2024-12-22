package com.museum.web.repositories;

import com.museum.web.entities.Role;
import com.museum.web.repositories.generics.ReadRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface RoleRepository extends ReadRepository<Role, Integer> {
    Optional<Role> findByValue(int value);
}
