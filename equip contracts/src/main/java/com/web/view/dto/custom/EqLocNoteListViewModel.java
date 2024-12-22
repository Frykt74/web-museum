package com.web.view.dto.custom;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record EqLocNoteListViewModel(
        BaseViewModel base,
        List<EquipmentNotesViewModel> equipmentsLocationsNotes,
        String currentUrl
) {
}
