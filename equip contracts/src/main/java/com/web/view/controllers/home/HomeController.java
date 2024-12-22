package com.web.view.controllers.home;

import com.web.view.controllers.base.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HomeController extends BaseController {
    /**
     * Отображает главную страницу
     */
    @GetMapping
    String index(Model model);
}

