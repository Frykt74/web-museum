package com.museum.web.services.impl;

import com.museum.web.dtos.category.CategoryDto;
import com.museum.web.entities.Category;
import com.museum.web.entities.Equipment;
import com.museum.web.entities.Location;
import com.museum.web.exceptions.CategoryNotFoundException;
import com.museum.web.repositories.CategoryRepository;
import com.museum.web.repositories.EquipmentRepository;
import com.museum.web.repositories.TicketRepository;
import com.museum.web.services.CategoryService;
import com.web.view.dto.custom.EquipmentNotesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EquipmentRepository equipmentRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               EquipmentRepository equipmentRepository,
                               TicketRepository ticketRepository) {
        this.categoryRepository = categoryRepository;
        this.equipmentRepository = equipmentRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CategoryDto findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return toDto(category);
    }

    @Override
    @Cacheable("pagesOfCategories")
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(this::toDto);
    }

    @Override
    @Cacheable("categoriesList")
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable("equipmentAndLocationsAndNotes")
    public List<EquipmentNotesViewModel> getEquipmentAndLocationsAndNoteByCategory(int categoryId, Integer visitorId) {
        List<Equipment> equipmentList = equipmentRepository.findAllByCategoryIdWithNotesFirst(categoryId);

        List<Integer> locationIds = equipmentList.stream()
                .map(eq -> eq.getLocation().getId())
                .distinct()
                .toList();

        Map<Integer, Long> ticketCounts = ticketRepository.countTicketsByLocationIds(locationIds);

        return equipmentList.stream()
                .map(eq -> {
                    Location location = eq.getLocation();
                    long popularity = ticketCounts.getOrDefault(location.getId(), 0L);
                    String note = eq.getNote() != null ? eq.getNote().getFact() : null;

                    return new EquipmentNotesViewModel(
                            eq.getId(),
                            eq.getName(),
                            eq.getDescription(),
                            location.getId(),
                            location.getName(),
                            eq.getImage(),
                            note,
                            popularity,
                            visitorId
                    );
                })
                .sorted(Comparator.comparingLong(EquipmentNotesViewModel::popularity).reversed())
                .toList();
    }

    private CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }
}
