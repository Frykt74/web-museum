package com.museum.web.controllers;

import com.museum.web.dtos.theme.ThemeDto;
import com.museum.web.services.ThemeService;
import com.museum.web.services.VisitorService;
import com.web.view.controllers.theme.ThemeController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.custom.EqLocListViewModel;
import com.web.view.dto.custom.EquipmentLocationViewModel;
import com.web.view.dto.theme.forms.ThemesSearchForm;
import com.web.view.dto.theme.models.ThemeDetailsViewModel;
import com.web.view.dto.theme.models.ThemeListViewModel;
import com.web.view.dto.theme.models.ThemeViewModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/theme")
public class ThemeControllerImpl implements ThemeController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final ThemeService themeService;
    private final VisitorService visitorService;

    public ThemeControllerImpl(ThemeService themeService, VisitorService visitorService) {
        this.themeService = themeService;
        this.visitorService = visitorService;
    }

    @Override
    @GetMapping
    public String listThemes(@ModelAttribute("form") ThemesSearchForm form, Model model) {
        LOG.info("View themes");

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 9;
        form = new ThemesSearchForm(searchTerm, page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ThemeDto> themesPage = themeService.findAll(pageable);

        List<ThemeViewModel> themeViewModels = themesPage.getContent().stream()
                .map(theme -> new ThemeViewModel(theme.id(), theme.name(), theme.description()))
                .toList();

        ThemeListViewModel viewModel = new ThemeListViewModel(
                createBaseViewModel("Список тем"),
                themeViewModels,
                themesPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "theme/theme-list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model) {
        LOG.info("View details of theme {}", id);

        ThemeDto theme = themeService.findById(id);
        ThemeDetailsViewModel viewModel = new ThemeDetailsViewModel(
                createBaseViewModel("Детали темы"),
                new ThemeViewModel(theme.id(), theme.name(), theme.description())
        );
        model.addAttribute("model", viewModel);
        return "theme/theme-details";
    }

    @Override
    @GetMapping("/{id}/equipments")
    public String viewEquipmentAndLocations(@PathVariable Integer id, Principal principal, Model model,
                                            HttpServletRequest request) {
        Integer visitorId = null;

        if (principal != null) {
            visitorId = visitorService.getVisitorIdByEmail(principal.getName());
            LOG.info("User {} view equipment in theme with id {}", principal.getName(), id);
        } else {
            LOG.info("View equipment in theme with id {}", id);
        }

        String currentUrl = request.getRequestURI();
        List<EquipmentLocationViewModel> equipmentLocations = themeService.getEquipmentAndLocationsByTheme(id);
        EqLocListViewModel viewModel = new EqLocListViewModel(new BaseViewModel("Техника в подборке"), equipmentLocations, visitorId, currentUrl);
        model.addAttribute("model", viewModel);
        return "theme/theme-equipment-view";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
