package com.museum.web.services;

import com.museum.web.dtos.category.CategoryDto;
import com.web.view.dto.custom.EquipmentNotesViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto findById(Integer id);

    Page<CategoryDto> findAll(Pageable pageable);

    List<CategoryDto> findAll();

    List<EquipmentNotesViewModel> getEquipmentAndLocationsAndNoteByCategory(int categoryId, Integer visitorId);
}
