package com.web.view.dto.equipment.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record EquipmentListViewModel(
        BaseViewModel base,
        List<EquipmentViewModel> equipments,
        Integer totalPages
) {
}
