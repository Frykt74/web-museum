package com.web.view.controllers.theme;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.theme.forms.ThemesSearchForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/themes")
public interface ThemeController extends BaseController {
    @GetMapping
    String listThemes(
            @ModelAttribute("form") ThemesSearchForm form,
            Model model
    );

    @GetMapping("/{id}")
    String details(
            @PathVariable Integer id,
            Model model
    );

    @GetMapping("/{id}/equipments")
    String viewEquipmentAndLocations(
            @PathVariable Integer id, Principal principal, Model model,
            HttpServletRequest request
    );
}

