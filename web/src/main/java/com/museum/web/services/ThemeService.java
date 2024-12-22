package com.museum.web.services;

import com.museum.web.dtos.theme.ThemeDto;
import com.museum.web.dtos.theme.ThemeEditDto;
import com.museum.web.dtos.theme.ThemeInputDto;
import com.web.view.dto.custom.EquipmentLocationViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ThemeService {
    ThemeDto findById(Integer id);

    Page<ThemeDto> findAll(Pageable pageable);

    List<ThemeDto> findAll();

    void addTheme(ThemeInputDto themeInputDto);

    void updateTheme(ThemeEditDto themeEditDto);

    List<EquipmentLocationViewModel> getEquipmentAndLocationsByTheme(int themeId);
}

