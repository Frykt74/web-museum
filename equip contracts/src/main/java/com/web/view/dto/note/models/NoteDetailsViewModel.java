package com.web.view.dto.note.models;

import com.web.view.dto.base.BaseViewModel;

public record NoteDetailsViewModel(
        BaseViewModel base,
        NoteViewModel note
) {
}

