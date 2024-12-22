package com.museum.web.services.impl;

import com.museum.web.dtos.equipment.EquipmentDto;
import com.museum.web.dtos.equipment.EquipmentEditDto;
import com.museum.web.dtos.equipment.EquipmentInputDto;
import com.museum.web.entities.*;
import com.museum.web.entities.enums.Country;
import com.museum.web.exceptions.CategoryNotFoundException;
import com.museum.web.exceptions.EquipmentNotFoundException;
import com.museum.web.exceptions.LocationNotFoundException;
import com.museum.web.exceptions.ThemeNotFoundException;
import com.museum.web.repositories.CategoryRepository;
import com.museum.web.repositories.EquipmentRepository;
import com.museum.web.repositories.LocationRepository;
import com.museum.web.repositories.ThemeRepository;
import com.museum.web.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EquipmentServiceImpl(
            EquipmentRepository equipmentRepository,
            CategoryRepository categoryRepository,
            ThemeRepository themeRepository,
            LocationRepository locationRepository) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
        this.themeRepository = themeRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public EquipmentDto findById(Integer id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
        return toDto(equipment);
    }

    @Override
    @CacheEvict(value = "pagesOfEquipment", allEntries = true)
    public void addEquipment(EquipmentInputDto equipmentInputDto) {
        Equipment equipment = toEntity(equipmentInputDto);
        equipmentRepository.save(equipment);
    }

    @Override
    @CachePut(value = "pagesOfEquipment", key = "#equipmentEditDto.id")
    public void updateEquipment(EquipmentEditDto equipmentEditDto) {
        Equipment equipment = equipmentRepository.findById(equipmentEditDto.id())
                .orElseThrow(() -> new EquipmentNotFoundException(equipmentEditDto.id()));

        equipment.setName(equipmentEditDto.name());
        equipment.setYear(equipmentEditDto.year());
        equipment.setCountry(Country.fromId(equipmentEditDto.country()));
        equipment.setDescription(equipmentEditDto.description());
        equipment.setImage(equipmentEditDto.image());

        Category category = categoryRepository.findById(equipmentEditDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(equipmentEditDto.categoryId()));
        equipment.setCategory(category);

        Theme theme = themeRepository.findById(equipmentEditDto.themeId())
                .orElseThrow(() -> new ThemeNotFoundException(equipmentEditDto.themeId()));
        equipment.setTheme(theme);

        Location location = locationRepository.findById(equipmentEditDto.locationId())
                .orElseThrow(() -> new LocationNotFoundException(equipmentEditDto.locationId()));
        equipment.setLocation(location);

        equipmentRepository.save(equipment);
    }

    @Override
    @Cacheable(value = "pagesOfEquipment", key = "#pageable.pageNumber")
    public Page<EquipmentDto> findAll(Pageable pageable) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(pageable);
        return equipmentPage.map(this::toDto);
    }

    private EquipmentDto toDto(Equipment equipment) {
        String fact = equipment.getNote() != null ? equipment.getNote().getFact() : null;
        return new EquipmentDto(
                equipment.getId(),
                equipment.getCategory().getId(),
                equipment.getTheme().getId(),
                equipment.getLocation().getId(),
                equipment.getName(),
                equipment.getYear(),
                equipment.getCountry().getId(),
                equipment.getDescription(),
                equipment.getImage(),
                equipment.getDate(),
                fact
        );
    }

    private Equipment toEntity(EquipmentInputDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));

        Theme theme = themeRepository.findById(dto.themeId())
                .orElseThrow(() -> new ThemeNotFoundException(dto.themeId()));

        Location location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new LocationNotFoundException(dto.locationId()));

        Equipment equipment = new Equipment(
                category,
                theme,
                location,
                dto.name(),
                dto.year(),
                Country.fromId(dto.country()),
                dto.description(),
                dto.image(),
                dto.date()
        );

        if (dto.note() != null) {
            Note note = new Note(equipment, dto.note());
            equipment.setNote(note);
        }

        return equipment;
    }
}
