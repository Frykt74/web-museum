package com.museum.web.services;

import com.museum.web.dtos.equipment.EquipmentNoteDto;
import com.museum.web.dtos.note.NoteDto;
import com.museum.web.dtos.note.NoteEditDto;
import com.museum.web.dtos.note.NoteInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {
    NoteDto findById(Integer id);

    Page<NoteDto> findAll(Pageable pageable);

    void addNote(NoteInputDto noteInputDto);

    void updateNote(NoteEditDto noteEditDto);

    void deleteNoteById(Integer id);

    Page<EquipmentNoteDto> getEquipmentWithNotes(Pageable pageable);
}
