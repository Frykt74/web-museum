package com.museum.web.controllers;

import com.museum.web.dtos.equipment.EquipmentDto;
import com.museum.web.services.CategoryService;
import com.museum.web.services.EquipmentService;
import com.museum.web.services.LocationService;
import com.museum.web.services.ThemeService;
import com.web.view.controllers.equipment.EquipmentController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.equipment.forms.EquipmentSearchForm;
import com.web.view.dto.equipment.models.EquipmentDetailsViewModel;
import com.web.view.dto.equipment.models.EquipmentListViewModel;
import com.web.view.dto.equipment.models.EquipmentViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/equipment")
public class EquipmentControllerImpl implements EquipmentController {
    private final Logger LOG = LogManager.getLogger(Controller.class);

    private final EquipmentService equipmentService;
    private final LocationService locationService;
    private final ThemeService themeService;
    private final CategoryService categoryService;

    @Autowired
    public EquipmentControllerImpl(EquipmentService equipmentService,
                                   LocationService locationService,
                                   ThemeService themeService,
                                   CategoryService categoryService) {
        this.equipmentService = equipmentService;
        this.locationService = locationService;
        this.themeService = themeService;
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public String listEquipments(@ModelAttribute("form") EquipmentSearchForm form, Model model, Principal principal) {
        if (principal != null) {
            LOG.info("The user {} browses through the list of equipments", principal.getName());
        } else {
            LOG.info("The user browses through the list of equipments");
        }

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 3;
        form = new EquipmentSearchForm(searchTerm, page, size);


        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EquipmentDto> equipmentPage = equipmentService.findAll(pageable);

        List<EquipmentViewModel> equipmentViewModels = equipmentPage.getContent().stream()
                .map(equipment -> new EquipmentViewModel(
                        equipment.id(),
                        equipment.name(),
                        locationService.findById(equipment.locationId()).name(),
                        categoryService.findById(equipment.categoryId()).name(),
                        themeService.findById(equipment.themeId()).name(),
                        equipment.year()
                ))
                .toList();

        EquipmentListViewModel viewModel = new EquipmentListViewModel(
                createBaseViewModel("Список техники"),
                equipmentViewModels,
                equipmentPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "equipment/equipment-list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model, Principal principal) {
        if (principal != null) {
            LOG.info("The user {} browses through the details of equipment", principal.getName());
        } else {
            LOG.info("The user browses through the details of equipment");
        }

        EquipmentDto equipment = equipmentService.findById(id);
        EquipmentDetailsViewModel viewModel = new EquipmentDetailsViewModel(
                createBaseViewModel("Детали техники"),
                new EquipmentViewModel(equipment.id(), equipment.name(),
                        locationService.findById(equipment.locationId()).name(),
                        categoryService.findById(equipment.categoryId()).name(),
                        themeService.findById(equipment.themeId()).name(),
                        equipment.year())
        );
        model.addAttribute("model", viewModel);
        return "equipment/equipment-details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
