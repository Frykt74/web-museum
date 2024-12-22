package com.web.view.controllers.category;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.category.forms.CategorySearchForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/categories")
public interface CategoryController extends BaseController {
    @GetMapping
    String listCategories(
            @ModelAttribute("form") CategorySearchForm form,
            Model model
    );

    @GetMapping("/{id}")
    String details(
            @PathVariable Integer id,
            Model model
    );
}

