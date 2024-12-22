package com.museum.web.services;

import com.museum.web.entities.Role;
import com.museum.web.entities.enums.UserRoles;
import com.museum.web.repositories.RoleRepository;

public interface RoleService {
    Role toEntity(UserRoles role, RoleRepository roleRepository);

    UserRoles mapEntityToRole(Role roleEntity);
}
