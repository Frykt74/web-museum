package com.museum.web.repositories.impl;

import com.museum.web.entities.Note;
import com.museum.web.repositories.NoteRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepositoryImpl extends CustomCrudRepositoryImpl<Note, Integer> implements NoteRepository {
    protected NoteRepositoryImpl() {
        super(Note.class);
    }
}
