package com.museum.web.repositories;

import com.museum.web.entities.Equipment;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface EquipmentRepository extends
        CreateRepository<Equipment, Integer>,
        ReadRepository<Equipment, Integer>,
        UpdateRepository<Equipment, Integer> {

    List<Equipment> findAllByThemeId(int themeId);

    List<Equipment> findAllByCategoryIdWithNotesFirst(int categoryId);

    Page<Object[]> findAllEquipmentWithOrWithoutNotes(Pageable pageable);
}
