package com.web.view.controllers.equipment;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.equipment.forms.EquipmentCreateForm;
import com.web.view.dto.equipment.forms.EquipmentEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin/equipment")
public interface AdminEquipmentController extends BaseController {
    @GetMapping("/create")
    String createForm(Model model, Principal principal);

    @PostMapping("/create")
    String create(
            @Valid @ModelAttribute("form") EquipmentCreateForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    );

    @GetMapping("/edit/{id}")
    String editForm(
            @PathVariable Integer id,
            Model model,
            Principal principal
    );

    @PostMapping("/edit/{id}")
    String edit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("form") EquipmentEditForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    );
}
