package com.museum.web.controllers.admins;

import com.museum.web.dtos.equipment.EquipmentNoteDto;
import com.museum.web.dtos.note.NoteDto;
import com.museum.web.dtos.note.NoteEditDto;
import com.museum.web.dtos.note.NoteInputDto;
import com.museum.web.exceptions.NoteNotFoundException;
import com.museum.web.services.EquipmentService;
import com.museum.web.services.NoteService;
import com.web.view.controllers.note.AdminNoteController;
import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.custom.EquipmentNoteListViewModel;
import com.web.view.dto.custom.EquipmentNoteSearchForm;
import com.web.view.dto.custom.EquipmentNoteViewModel;
import com.web.view.dto.note.forms.NoteCreateForm;
import com.web.view.dto.note.forms.NoteEditForm;
import com.web.view.dto.note.forms.NoteSearchForm;
import com.web.view.dto.note.models.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/note")
public class AdminNoteControllerImpl implements AdminNoteController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private NoteService noteService;
    private EquipmentService equipmentService;

    @Autowired
    public void setNoteService(NoteService noteService,
                               EquipmentService equipmentService) {
        this.noteService = noteService;
        this.equipmentService = equipmentService;
    }

    @Override
    @GetMapping("/create")
    public String createForm(@RequestParam("equipmentId") Integer equipmentId, Model model,
                             Principal principal) {
        LOG.info("Create new note form by {}", principal.getName());


        NoteCreateViewModel viewModel = new NoteCreateViewModel(createBaseViewModel("Создание записи"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new NoteCreateForm(equipmentId, ""));
        return "note/note-create";
    }

    @Override
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") NoteCreateForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Incorrect information when adding new note.");

            NoteCreateViewModel viewModel = new NoteCreateViewModel(createBaseViewModel("Создание записи"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "note/note-create";
        }

        NoteInputDto noteInputDto = new NoteInputDto(form.equipmentId(), form.fact());
        noteService.addNote(noteInputDto);
        return "redirect:/admin/note";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("Create note form by {}", principal.getName());

        NoteDto note = noteService.findById(id);
        NoteEditViewModel viewModel = new NoteEditViewModel(createBaseViewModel("Редактирование запись"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new NoteEditForm(note.id(), note.equipmentId(), note.fact()));
        return "note/note-edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("form") NoteEditForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            LOG.warn("Incorrect information when editing note.");

            NoteEditViewModel viewModel = new NoteEditViewModel(createBaseViewModel("Редактирование записи"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "note/note-edit";
        }

        NoteEditDto noteEditDto = new NoteEditDto(id, form.equipmentId(), form.fact());
        LOG.info("Successfully edited note {} by {}", id, principal.getName());
        noteService.updateNote(noteEditDto);
        return "redirect:/admin/note";
    }

    @Override
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id,
                         Principal principal) {
        try {
            LOG.info("Delete note with id {} by {}", id, principal.getName());
            noteService.deleteNoteById(id);
            return "redirect:/admin/note";
        } catch (NoteNotFoundException e) {
            LOG.error("Deletion of the note with id {} failed", id, e);
            return "redirect:/admin/error";
        }
    }

    @GetMapping
    public String listEquipmentWithNotes(@ModelAttribute("form") EquipmentNoteSearchForm form, Model model, Principal principal) {
        LOG.info("Browsing equipment with notes by {}", principal.getName());

        String searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        int page = form.page() != null ? form.page() : 1;
        int size = form.size() != null ? form.size() : 10;
        form = new EquipmentNoteSearchForm(searchTerm, page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EquipmentNoteDto> equipmentPage = noteService.getEquipmentWithNotes(pageable);

        List<EquipmentNoteViewModel> equipmentViewModels = equipmentPage.getContent().stream()
                .map(dto -> new EquipmentNoteViewModel(
                        dto.equipmentId(),
                        dto.equipmentName(),
                        dto.note(),
                        dto.noteId()
                ))
                .toList();

        EquipmentNoteListViewModel viewModel = new EquipmentNoteListViewModel(
                "Список техники с заметками",
                equipmentViewModels,
                equipmentPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "note/note-list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("View {} note details by {}", id, principal.getName());

        NoteDto note = noteService.findById(id);
        NoteDetailsViewModel viewModel = new NoteDetailsViewModel(
                createBaseViewModel("Детали темы"),
                new NoteViewModel(
                        note.id(),
                        equipmentService.findById(note.equipmentId()).name(),
                        note.fact())
        );
        model.addAttribute("model", viewModel);
        return "note/note-details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
