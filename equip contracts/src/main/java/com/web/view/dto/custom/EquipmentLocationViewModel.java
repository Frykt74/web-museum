package com.web.view.dto.custom;

import com.web.view.dto.base.BaseViewModel;

public record EquipmentLocationViewModel(
        Integer equipmentId,
        String equipmentName,
        String equipmentDescription,
        Integer locationId,
        String locationName,
        String image,
        Long popularity // must be hidden?
) {
}
