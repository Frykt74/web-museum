package com.web.view.controllers.note;

import com.web.view.controllers.base.BaseController;
import com.web.view.dto.note.forms.NoteCreateForm;
import com.web.view.dto.note.forms.NoteEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin/note")
public interface AdminNoteController extends BaseController {
    @GetMapping("/create")
    String createForm(@RequestParam("equipmentId") Integer equipmentId, Model model,
                      Principal principal);

    @PostMapping("/create")
    String create(
            @Valid @ModelAttribute("form") NoteCreateForm form,
            BindingResult bindingResult,
            Model model
    );

    @GetMapping("/{id}/edit")
    String editForm(@PathVariable Integer id, Model model, Principal principal);

    @PostMapping("/{id}/edit")
    String edit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("form") NoteEditForm form,
            BindingResult bindingResult,
            Model model,
            Principal principal
    );

    @GetMapping("/{id}/delete")
    String delete(@PathVariable Integer id,
                  Principal principal);
}
