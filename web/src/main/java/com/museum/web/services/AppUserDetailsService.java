package com.museum.web.services;

import com.museum.web.entities.Visitor;
import com.museum.web.repositories.VisitorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AppUserDetailsService implements UserDetailsService {
    private final VisitorRepository visitorRepository;

    public AppUserDetailsService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return visitorRepository.findByEmail(email) // username - это email
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Visitor with email " + email + " not found"));
    }


    private UserDetails mapToUserDetails(Visitor visitor) {
        List<GrantedAuthority> authorities = visitor.getRole() != null
                ? List.of(new SimpleGrantedAuthority("ROLE_" + visitor.getRole().getName()))
                : List.of();

        return new org.springframework.security.core.userdetails.User(
                visitor.getEmail(),
                visitor.getPassword(),
                authorities
        );
    }
}
