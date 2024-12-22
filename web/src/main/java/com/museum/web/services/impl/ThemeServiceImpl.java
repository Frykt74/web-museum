package com.museum.web.services.impl;

import com.museum.web.dtos.theme.ThemeDto;
import com.museum.web.dtos.theme.ThemeEditDto;
import com.museum.web.dtos.theme.ThemeInputDto;
import com.museum.web.entities.Equipment;
import com.museum.web.entities.Location;
import com.museum.web.entities.Theme;
import com.museum.web.exceptions.ThemeNotFoundException;
import com.museum.web.repositories.EquipmentRepository;
import com.museum.web.repositories.ThemeRepository;
import com.museum.web.repositories.TicketRepository;
import com.museum.web.services.ThemeService;
import com.web.view.dto.custom.EquipmentLocationViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService {
    private final ThemeRepository themeRepository;
    private final EquipmentRepository equipmentRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository,
                            EquipmentRepository equipmentRepository,
                            TicketRepository ticketRepository) {
        this.themeRepository = themeRepository;
        this.equipmentRepository = equipmentRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ThemeDto findById(Integer id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException(id));
        return toDto(theme);
    }

    @Override
    @Cacheable(value = "pagesOfTheme", key = "#pageable.pageNumber")
    public Page<ThemeDto> findAll(Pageable pageable) {
        Page<Theme> page = themeRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    @Override
    @Cacheable("themes")
    public List<ThemeDto> findAll() {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "pagesOfTheme", allEntries = true)
    public void addTheme(ThemeInputDto themeInputDto) {
        Theme theme = toEntity(themeInputDto);
        themeRepository.save(theme);
    }

    @Override
    @CachePut(value = "pagesOfTheme", key = "#themeEditDto.id")
    public void updateTheme(ThemeEditDto themeEditDto) {
        Theme theme = themeRepository.findById(themeEditDto.id())
                .orElseThrow(() -> new ThemeNotFoundException(themeEditDto.id()));
        theme.setName(themeEditDto.name());
        theme.setDescription(themeEditDto.description());
        themeRepository.save(theme);
    }

    @Override
    public List<EquipmentLocationViewModel> getEquipmentAndLocationsByTheme(int themeId) {
        List<Equipment> equipmentList = equipmentRepository.findAllByThemeId(themeId);

        List<Integer> locationIds = equipmentList.stream()
                .map(eq -> eq.getLocation().getId())
                .distinct()
                .toList();

        Map<Integer, Long> ticketCounts = ticketRepository.countTicketsByLocationIds(locationIds);

        return equipmentList.stream()
                .map(eq -> {
                    Location location = eq.getLocation();
                    long popularity = ticketCounts.getOrDefault(location.getId(), 0L);
                    return new EquipmentLocationViewModel(
                            eq.getId(),
                            eq.getName(),
                            eq.getDescription(),
                            location.getId(),
                            location.getName(),
                            eq.getImage(),
                            popularity
                    );
                })
                .sorted(Comparator.comparingLong(EquipmentLocationViewModel::popularity).reversed())
                .toList();
    }

    private ThemeDto toDto(Theme theme) {
        return new ThemeDto(theme.getId(), theme.getName(), theme.getDescription());
    }

    private Theme toEntity(ThemeInputDto dto) {
        return new Theme(dto.name(), dto.description());
    }
}
