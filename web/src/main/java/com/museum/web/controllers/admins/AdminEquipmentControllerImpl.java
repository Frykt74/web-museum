package com.museum.web.controllers.admins;

import com.museum.web.dtos.equipment.EquipmentDto;
import com.museum.web.dtos.equipment.EquipmentEditDto;
import com.museum.web.dtos.equipment.EquipmentInputDto;
import com.museum.web.services.CategoryService;
import com.museum.web.services.EquipmentService;
import com.museum.web.services.LocationService;
import com.museum.web.services.ThemeService;
import com.web.view.controllers.equipment.AdminEquipmentController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.equipment.forms.EquipmentCreateForm;
import com.web.view.dto.equipment.forms.EquipmentEditForm;
import com.web.view.dto.equipment.models.EquipmentCreateViewModel;
import com.web.view.dto.equipment.models.EquipmentEditViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Year;
import java.util.Date;

@Controller
@RequestMapping("/admin/equipment")
public class AdminEquipmentControllerImpl implements AdminEquipmentController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final EquipmentService equipmentService;
    private final CategoryService categoryService;
    private final ThemeService themeService;
    private final LocationService locationService;

    @Autowired
    public AdminEquipmentControllerImpl(EquipmentService equipmentService,
                                        CategoryService categoryService,
                                        ThemeService themeService,
                                        LocationService locationService) {
        this.equipmentService = equipmentService;
        this.categoryService = categoryService;
        this.themeService = themeService;
        this.locationService = locationService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model, Principal principal) {
        LOG.info("Create new equipment form by {}", principal.getName());

        EquipmentCreateViewModel viewModel = new EquipmentCreateViewModel(createBaseViewModel("Создание техники"));
        model.addAttribute("model", viewModel);

        model.addAttribute("form", new EquipmentCreateForm(
                "", "", null, null,
                null, null, 1, "", null
        ));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("locations", locationService.findAll());

        return "equipment/equipment-create";
    }

    @Override
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") EquipmentCreateForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Incorrect information when adding new equipment.");

            System.out.println(bindingResult.getAllErrors());
            EquipmentCreateViewModel viewModel = new EquipmentCreateViewModel(createBaseViewModel("Создание техники"));

            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("locations", locationService.findAll());

            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);

            return "equipment/equipment-create";
        }

        EquipmentInputDto equipmentInputDto = new EquipmentInputDto(
                form.categoryId(),
                form.themeId(),
                form.locationId(),
                form.name(),
                Year.parse(form.year()),
                form.countryId(),
                form.description(),
                form.image(),
                new Date(),
                form.note()
        );
        LOG.info("Create new equipment {} by {}", equipmentInputDto.name(), principal.getName());
        equipmentService.addEquipment(equipmentInputDto);
        return "redirect:/equipment";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("Create edit equipment form by {}", principal.getName());

        EquipmentDto equipment = equipmentService.findById(id);
        EquipmentEditViewModel viewModel = new EquipmentEditViewModel(createBaseViewModel("Редактирование техники"));

        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EquipmentEditForm(
                equipment.id(),
                equipment.name(),
                equipment.description(),
                equipment.categoryId(),
                equipment.themeId(),
                equipment.locationId(),
                equipment.year(),
                equipment.country(),
                equipment.image()
        ));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("locations", locationService.findAll());

        return "equipment/equipment-edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("form") EquipmentEditForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Incorrect information when editing equipment.");

            EquipmentEditViewModel viewModel = new EquipmentEditViewModel(createBaseViewModel("Редактирование техники"));

            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);

            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("locations", locationService.findAll());

            return "equipment/equipment-edit";
        }

        EquipmentEditDto equipmentEditDto = new EquipmentEditDto(
                id,
                form.categoryId(),
                form.themeId(),
                form.locationId(),
                form.name(),
                form.year(),
                form.countryId(),
                form.description(),
                form.image()
        );

        LOG.info("Successfully edited equipment {} by {}", id, principal.getName());
        equipmentService.updateEquipment(equipmentEditDto);
        return "redirect:/equipment";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
