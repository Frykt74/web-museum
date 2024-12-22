package com.museum.web.services.impl;

import com.museum.web.entities.Role;
import com.museum.web.entities.enums.UserRoles;
import com.museum.web.repositories.RoleRepository;
import com.museum.web.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Role toEntity(UserRoles role, RoleRepository roleRepository) {
        return roleRepository.findByValue(role.getValue())
                .orElseThrow(() -> new IllegalArgumentException("Role not found for value: " + role.getValue()));
    }

    @Override
    public UserRoles mapEntityToRole(Role roleEntity) {
        return UserRoles.fromValue(roleEntity.getId());
    }
}
