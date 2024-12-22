package com.museum.web.controllers;

import com.museum.web.dtos.ticket.TicketDto;
import com.museum.web.services.LocationService;
import com.museum.web.services.TicketService;
import com.museum.web.services.VisitorService;
import com.web.view.controllers.base.BaseController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.ticket.forms.TicketSearchForm;
import com.web.view.dto.ticket.models.TicketListViewModel;
import com.web.view.dto.ticket.models.TicketViewModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketControllerImpl implements BaseController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final TicketService ticketService;
    private final VisitorService visitorService;
    private final LocationService locationService;

    @Autowired
    public TicketControllerImpl(TicketService ticketService,
                                VisitorService visitorService,
                                LocationService locationService) {
        this.ticketService = ticketService;
        this.visitorService = visitorService;
        this.locationService = locationService;
    }

    @GetMapping("/cart")
    public String cart(@ModelAttribute("form") TicketSearchForm form,
                       Principal principal,
                       Model model) {
        LOG.info("The user {} is browsing the shopping basket.", principal.getName());

        Integer visitorId = visitorService.getVisitorIdByEmail(principal.getName());

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new TicketSearchForm(searchTerm, page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TicketDto> ticketPage = ticketService.findUnpaidTickets(visitorId, pageable);

        List<TicketViewModel> ticketViewModels = ticketPage.getContent().stream()
                .map(ticket -> new TicketViewModel(
                                ticket.id(),
                                visitorService.findById(ticket.visitorId()).fullNameWithInitials(),
                                locationService.findById(ticket.locationId()).name(),
                                ticket.date(),
                                ticket.isPaid(),
                                ticket.visitorId()
                        )
                )
                .toList();

        TicketListViewModel viewModel = new TicketListViewModel(
                createBaseViewModel("Корзина"),
                ticketViewModels,
                ticketPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "cart";
    }

    @PostMapping("/cart/pay/{ticketId}")
    public String payTicket(@PathVariable Integer ticketId, Principal principal) {
        try {
            ticketService.getPayment(ticketId);
            LOG.info("The user {} has paid for the ticket {}.", principal.getName(), ticketId);
        } catch (IllegalArgumentException e) {
            LOG.error("Critical error of ticket {} payment", ticketId, e);
            return "redirect:/ticket/cart";
        }

        return "redirect:/ticket/cart";
    }

    @GetMapping("/history")
    public String paidTickets(@ModelAttribute("form") TicketSearchForm form,
                              Principal principal,
                              Model model) {
        LOG.info("The user {} views the purchase history.", principal.getName());

        Integer visitorId = visitorService.getVisitorIdByEmail(principal.getName());
        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new TicketSearchForm(searchTerm, page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TicketDto> ticketPage = ticketService.findPaidTickets(visitorId, pageable);

        List<TicketViewModel> ticketViewModels = ticketPage.getContent().stream()
                .map(ticket -> new TicketViewModel(
                                ticket.id(),
                                visitorService.findById(ticket.visitorId()).fullNameWithInitials(),
                                locationService.findById(ticket.locationId()).name(),
                                ticket.date(),
                                ticket.isPaid(),
                                ticket.visitorId()
                        )
                )
                .toList();

        TicketListViewModel viewModel = new TicketListViewModel(
                createBaseViewModel("Оплаченные билеты"),
                ticketViewModels,
                ticketPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "paid-tickets";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Integer locationId,
                            @RequestParam String redirectUrl,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        Integer visitorId = visitorService.getVisitorIdByEmail(principal.getName());

        try {
            ticketService.addTicketToCart(visitorId, locationId);
            LOG.info("A user {} has added a ticket to {} in their basket.", principal.getName(), locationService.findById(locationId));
            redirectAttributes.addFlashAttribute("message", "Билет добавлен в корзину");
        } catch (Exception e) {
            LOG.error("Critical error of ticket add to the basket", e);
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении билета: " + e.getMessage());
        }

        return "redirect:" + redirectUrl;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
