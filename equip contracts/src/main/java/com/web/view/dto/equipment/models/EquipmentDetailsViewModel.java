package com.web.view.dto.equipment.models;

import com.web.view.dto.base.BaseViewModel;

public record EquipmentDetailsViewModel(
        BaseViewModel base,
        EquipmentViewModel equipment
) {
}
