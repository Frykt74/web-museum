package com.web.view.dto.custom;

import java.io.Serializable;

public record EquipmentNotesViewModel(
        Integer equipmentId,
        String equipmentName,
        String equipmentDescription,
        Integer locationId,
        String locationName,
        String image,
        String note,
        Long popularity,
        Integer visitorId
) implements Serializable {
}
