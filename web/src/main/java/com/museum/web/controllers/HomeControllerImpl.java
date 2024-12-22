package com.museum.web.controllers;

import com.web.view.controllers.home.HomeController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.home.HomeViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Override
    @GetMapping("/")
    public String index(Model model) {
        LOG.info("Home page is open");

        HomeViewModel viewModel = new HomeViewModel(createBaseViewModel("Главная страница"));
        model.addAttribute("viewModel", viewModel);

        return "index";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
