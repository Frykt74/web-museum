package com.museum.web.controllers;

import com.museum.web.dtos.visitor.VisitorDto;
import com.museum.web.dtos.visitor.VisitorEditDto;
import com.museum.web.exceptions.IllegalVisitorException;
import com.museum.web.services.VisitorService;
import com.web.view.controllers.visitor.VisitorController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.visitor.forms.VisitorEditForm;
import com.web.view.dto.visitor.models.VisitorDetailsViewModel;
import com.web.view.dto.visitor.models.VisitorEditViewModel;
import com.web.view.dto.visitor.models.VisitorViewModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/visitor")
public class VisitorControllerImpl implements VisitorController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final VisitorService visitorService;

    public VisitorControllerImpl(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @Override
    @GetMapping
    public String details(Principal principal, Model model) {
        LOG.info("Fetching visitor details for user: {}", principal.getName());

        VisitorDto visitor = visitorService.findByEmail(principal.getName());
        VisitorDetailsViewModel viewModel = new VisitorDetailsViewModel(
                createBaseViewModel("Профиль"),
                new VisitorViewModel(
                        visitor.id(),
                        visitor.surname(),
                        visitor.firstname(),
                        visitor.patronymic(),
                        visitor.email(),
                        visitor.phone()
                )
        );

        model.addAttribute("model", viewModel);
        return "visitor/visitor-details";
    }

    @Override
    @GetMapping("/edit")
    public String editForm(Principal principal, Model model) {
        VisitorDto visitor = visitorService.findByEmail(principal.getName());
        VisitorEditViewModel viewModel = new VisitorEditViewModel(createBaseViewModel("Редактирование посетителя"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new VisitorEditForm(
                visitor.surname(),
                visitor.firstname(),
                visitor.patronymic(),
                visitor.email(),
                visitor.phone()
        ));
        return "visitor/visitor-edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute("form") VisitorEditForm form,
            BindingResult bindingResult,
            Principal principal,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Form validation failed: {}", bindingResult.getAllErrors());
            LOG.warn("User edit failed: {}", principal.getName());

            System.out.println(bindingResult.getAllErrors());
            VisitorEditViewModel viewModel = new VisitorEditViewModel(createBaseViewModel("Редактирование посетителя"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "visitor/visitor-edit";
        }
        LOG.info("Updating details for user: {}", principal.getName());

        Integer id = visitorService.getVisitorIdByEmail(principal.getName());

        try {
            VisitorEditDto visitorEditDto = new VisitorEditDto(
                    form.surname(),
                    form.firstname(),
                    form.patronymic(),
                    form.email(),
                    form.phone()
            );
            visitorService.updateVisitor(visitorEditDto, id);

        } catch (IllegalVisitorException e) {
            LOG.warn("Error updating details for user: {}", principal.getName(), e);

            bindingResult.rejectValue("email", "error.form", e.getMessage());
            VisitorEditViewModel viewModel = new VisitorEditViewModel(createBaseViewModel("Редактирование посетителя"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "visitor/visitor-edit";
        }
        LOG.info("Successfully updated details for user: {}", principal.getName());
        return "redirect:/visitor";
    }

    @PostMapping("/delete")
    public String deleteProfile(Principal principal,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                RedirectAttributes redirectAttributes) {
        LOG.info("Deleting profile for user: {}", principal.getName());
        try {
            visitorService.deleteProfile(principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Ваш профиль успешно удален");

            // завершение сессии
            new SecurityContextLogoutHandler().logout(request, response, null);
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOG.error("Failed to delete profile for user: {}", principal.getName(), e);

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/visitor";
        }
        return "redirect:/";
    }


    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
