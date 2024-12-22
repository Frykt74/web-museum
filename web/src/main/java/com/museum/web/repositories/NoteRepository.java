package com.museum.web.repositories;

import com.museum.web.entities.Equipment;
import com.museum.web.entities.Note;
import com.museum.web.repositories.generics.CreateRepository;
import com.museum.web.repositories.generics.DeleteRepository;
import com.museum.web.repositories.generics.ReadRepository;
import com.museum.web.repositories.generics.UpdateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NoteRepository extends
        CreateRepository<Note, Integer>,
        ReadRepository<Note, Integer>,
        UpdateRepository<Note, Integer>,
        DeleteRepository<Note, Integer> {
}
