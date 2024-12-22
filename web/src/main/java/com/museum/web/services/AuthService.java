package com.museum.web.services;

import com.museum.web.dtos.visitor.RegistrationDto;
import com.museum.web.dtos.visitor.VisitorDto;

public interface AuthService {
    void register(RegistrationDto visitorRegistrationDto);

    VisitorDto findByEmail(String email);
}
