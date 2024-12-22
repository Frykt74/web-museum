package com.web.view.controllers.equipment;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.equipment.forms.EquipmentSearchForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/equipment")
public interface EquipmentController extends BaseController {
    @GetMapping
    String listEquipments(
            @ModelAttribute("form") EquipmentSearchForm form,
            Model model,
            Principal principal
    );

    @GetMapping("/{id}")
    String details(
            @PathVariable Integer id,
            Model model,
            Principal principal
    );
}

