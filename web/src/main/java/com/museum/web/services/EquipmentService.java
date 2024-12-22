package com.museum.web.services;

import com.museum.web.dtos.equipment.EquipmentDto;
import com.museum.web.dtos.equipment.EquipmentEditDto;
import com.museum.web.dtos.equipment.EquipmentInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentService {
    EquipmentDto findById(Integer id);

    void addEquipment(EquipmentInputDto equipmentInputDto);

    void updateEquipment(EquipmentEditDto equipmentEditDto);

    Page<EquipmentDto> findAll(Pageable pageable);
}
