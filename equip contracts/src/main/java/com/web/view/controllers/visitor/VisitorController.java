package com.web.view.controllers.visitor;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.visitor.forms.VisitorCreateForm;
import com.web.view.dto.visitor.forms.VisitorEditForm;
import com.web.view.dto.visitor.forms.VisitorSearchForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/visitor")
public interface VisitorController extends BaseController {

    @GetMapping
    String details(Principal principal, Model model);

    @GetMapping("/edit")
    String editForm(Principal principal, Model model);

    @PostMapping("/edit")
    String edit(
            @Valid @ModelAttribute("form") VisitorEditForm form,
            BindingResult bindingResult,
            Principal principal,
            Model model
    );
}
