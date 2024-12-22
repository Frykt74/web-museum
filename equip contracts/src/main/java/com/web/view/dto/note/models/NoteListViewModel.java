package com.web.view.dto.note.models;

import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.theme.models.ThemeViewModel;

import java.util.List;

public record NoteListViewModel(
        BaseViewModel base,
        List<NoteViewModel> notes,
        Integer totalPages
) {
}