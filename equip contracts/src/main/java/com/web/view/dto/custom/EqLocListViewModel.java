package com.web.view.dto.custom;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record EqLocListViewModel(
        BaseViewModel base,
        List<EquipmentLocationViewModel> equipmentLocations,
        Integer visitorId,
        String currentUrl
) {
}
