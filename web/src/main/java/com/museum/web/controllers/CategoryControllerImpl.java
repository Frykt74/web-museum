package com.museum.web.controllers;

import com.museum.web.dtos.category.CategoryDto;
import com.museum.web.services.CategoryService;
import com.museum.web.services.VisitorService;
import com.web.view.controllers.category.CategoryController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.category.forms.CategorySearchForm;
import com.web.view.dto.category.models.CategoryDetailsViewModel;
import com.web.view.dto.category.models.CategoryListViewModel;
import com.web.view.dto.category.models.CategoryViewModel;
import com.web.view.dto.custom.EqLocListViewModel;
import com.web.view.dto.custom.EqLocNoteListViewModel;
import com.web.view.dto.custom.EquipmentLocationViewModel;
import com.web.view.dto.custom.EquipmentNotesViewModel;
import com.web.view.dto.theme.forms.ThemesSearchForm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final CategoryService categoryService;
    private final VisitorService visitorService;

    @Autowired
    public CategoryControllerImpl(CategoryService categoryService, VisitorService visitorService) {
        this.categoryService = categoryService;
        this.visitorService = visitorService;
    }

    @Override
    @GetMapping
    public String listCategories(@ModelAttribute("form") CategorySearchForm form, Model model) {
        LOG.info("Calling categories");

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 9;
        form = new CategorySearchForm(searchTerm, page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CategoryDto> categoriesPage = categoryService.findAll(pageable);

        List<CategoryViewModel> categoryViewModels = categoriesPage.getContent().stream()
                .map(category -> new CategoryViewModel(category.id(), category.name(), category.description()))
                .toList();

        CategoryListViewModel viewModel = new CategoryListViewModel(
                createBaseViewModel("Список категорий"),
                categoryViewModels,
                categoriesPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "category-list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model) {
        LOG.info("View category information");

        CategoryDto category = categoryService.findById(id);
        CategoryDetailsViewModel viewModel = new CategoryDetailsViewModel(
                createBaseViewModel("Детали категории"),
                new CategoryViewModel(category.id(), category.name(), category.description())
        );
        model.addAttribute("model", viewModel);
        return "category-details";
    }

    @GetMapping("/{categoryId}/equipments")
    public String viewEquipmentAndNotes(@PathVariable Integer categoryId,
                                        Principal principal,
                                        Model model,
                                        HttpServletRequest request) {
        Integer visitorId = null;

        if (principal != null) {
            visitorId = visitorService.getVisitorIdByEmail(principal.getName());
            LOG.info("The user {} browses through the techniques in a category.", principal.getName());
        } else {
            LOG.info("Reviews the techniques in the category.");
        }
        String currentUrl = request.getRequestURI();
        List<EquipmentNotesViewModel> equipmentNotes = categoryService.getEquipmentAndLocationsAndNoteByCategory(categoryId, visitorId);
        EqLocNoteListViewModel viewModel = new EqLocNoteListViewModel(new BaseViewModel("Техника в категории"), equipmentNotes, currentUrl);
        model.addAttribute("model", viewModel);
        return "category-equipment-view";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
