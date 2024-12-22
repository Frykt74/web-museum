package com.museum.web.services.impl;

import com.museum.web.dtos.visitor.RegistrationDto;
import com.museum.web.dtos.visitor.VisitorDto;
import com.museum.web.entities.Role;
import com.museum.web.entities.Visitor;
import com.museum.web.entities.enums.UserRoles;
import com.museum.web.exceptions.IllegalVisitorException;
import com.museum.web.exceptions.VisitorNotFoundException;
import com.museum.web.repositories.RoleRepository;
import com.museum.web.repositories.VisitorRepository;
import com.museum.web.services.AuthService;
import com.museum.web.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final VisitorRepository visitorRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(VisitorRepository visitorRepository,
                           RoleRepository roleRepository,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.visitorRepository = visitorRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegistrationDto visitorRegistrationDto) {
        if (!visitorRegistrationDto.password().equals(visitorRegistrationDto.confirmPassword())) {
            throw new IllegalVisitorException("Passwords don't match");
        }

        if (visitorRepository.existsByEmail(visitorRegistrationDto.email())) {
            throw new IllegalVisitorException("Email already exists");
        }

        if (visitorRepository.existsByPhone(visitorRegistrationDto.phone())) {
            throw new IllegalVisitorException("This number already exists");
        }


        Role defaultRole = roleService.toEntity(UserRoles.USER, roleRepository);

        Visitor visitor = new Visitor(
                visitorRegistrationDto.surname(),
                visitorRegistrationDto.firstname(),
                visitorRegistrationDto.patronymic(),
                visitorRegistrationDto.email(),
                visitorRegistrationDto.phone(),
                passwordEncoder.encode(visitorRegistrationDto.password()),
                defaultRole
        );
        visitorRepository.save(visitor);
    }

    @Override
    public VisitorDto findByEmail(String email) {
        Visitor visitor = visitorRepository.findByEmail(email)
                .orElseThrow(() -> new VisitorNotFoundException(email));

        return new VisitorDto(
                visitor.getId(),
                visitor.getSurname(),
                visitor.getFirstname(),
                visitor.getPatronymic(),
                visitor.getEmail(),
                visitor.getPhone()
        );
    }
}
