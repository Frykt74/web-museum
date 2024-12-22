package com.museum.web.controllers;

import com.museum.web.dtos.visitor.RegistrationDto;
import com.museum.web.exceptions.IllegalVisitorException;
import com.museum.web.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        LOG.info("Accessing the registration form.");
        model.addAttribute("registrationDto", new RegistrationDto("", "", "",
                "", "", "", ""));
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationDto registrationDto, Model model) {
        LOG.info("Attempting to register user: {}", registrationDto.email());

        try {
            authService.register(registrationDto);
            model.addAttribute("successMessage", "Registration successful!");
            return "auth/login";
        } catch (IllegalVisitorException e) {
            LOG.warn("Registration failed for user: {}", registrationDto.email(), e);

            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        LOG.info("Accessing the login form.");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        LOG.info("User {} logged out.", principal.getName());

        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/";
    }


    @GetMapping("/login-error")
    public String loginError(Model model) {
        LOG.warn("Login error occurred.");

        model.addAttribute("loginError", true);
        return "auth/login";
    }

}
