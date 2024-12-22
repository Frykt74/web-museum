package com.museum.web.config;

import com.museum.web.entities.enums.UserRoles;
import com.museum.web.repositories.VisitorRepository;
import com.museum.web.services.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@Configuration
public class SecurityConfig {
    private final VisitorRepository visitorRepository;

    public SecurityConfig(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeHttpRequest -> authorizeHttpRequest
//                                Всем
                                .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/", "/users/**", "/public/**").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/category/**", "/theme/**", "/equipment/**").permitAll()
//                                User
                                .requestMatchers("/visitor/**", "/ticket/**").hasAnyRole(UserRoles.USER.name(), UserRoles.ADMIN.name(), UserRoles.MODERATOR.name())
//                                ADMIN MODERATOR
                                .requestMatchers("/admin/**").hasAnyRole(UserRoles.ADMIN.name(), UserRoles.MODERATOR.name())
                                .anyRequest().authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureUrl("/users/login-error")
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .securityContext(
                        securityContext -> securityContext.securityContextRepository(securityContextRepository)
                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsService(visitorRepository);
    }
}
