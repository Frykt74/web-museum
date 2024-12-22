package com.museum.web.services.impl;

import com.museum.web.dtos.equipment.EquipmentNoteDto;
import com.museum.web.dtos.note.NoteDto;
import com.museum.web.dtos.note.NoteEditDto;
import com.museum.web.dtos.note.NoteInputDto;
import com.museum.web.entities.Equipment;
import com.museum.web.entities.Note;
import com.museum.web.exceptions.EquipmentNotFoundException;
import com.museum.web.exceptions.NoteNotFoundException;
import com.museum.web.repositories.EquipmentRepository;
import com.museum.web.repositories.NoteRepository;
import com.museum.web.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository,
                           EquipmentRepository equipmentRepository) {
        this.noteRepository = noteRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public NoteDto findById(Integer id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        return toDto(note);
    }

    @Override
    @Cacheable("pagesOfNotes")
    public Page<NoteDto> findAll(Pageable pageable) {
        Page<Note> page = noteRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    @Override
    @CacheEvict(value = "pagesOfNotes", allEntries = true)
    public void addNote(NoteInputDto noteInputDto) {
        Note note = toEntity(noteInputDto);
        noteRepository.save(note);
    }

    @Override
    @CachePut(value = "pagesOfNotes", key = "#noteEditDto.id")
    public void updateNote(NoteEditDto noteEditDto) {
        Note note = noteRepository.findById(noteEditDto.id())
                .orElseThrow(() -> new NoteNotFoundException(noteEditDto.id()));

        Equipment equipment = equipmentRepository.findById(noteEditDto.equipmentId())
                .orElseThrow(() -> new EquipmentNotFoundException(noteEditDto.equipmentId()));

        note.setEquipment(equipment);
        note.setFact(noteEditDto.fact());

        noteRepository.save(note);
    }

    @Override
    @CacheEvict(value = "pagesOfNotes", key = "#id")
    public void deleteNoteById(Integer id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        noteRepository.deleteById(id);
    }

    @Override
    @Cacheable("pagesOfEquipmentsAndNotes")
    public Page<EquipmentNoteDto> getEquipmentWithNotes(Pageable pageable) {
        Page<Object[]> page = equipmentRepository.findAllEquipmentWithOrWithoutNotes(pageable);

        List<EquipmentNoteDto> dtos = page.getContent().stream()
                .map(row -> {
                    Equipment equipment = (Equipment) row[0];
                    Note note = (Note) row[1];
                    return new EquipmentNoteDto(
                            equipment.getId(),
                            equipment.getName(),
                            note != null ? note.getFact() : "",
                            note != null ? note.getId() : null
                    );
                })
                .toList();

        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }


    private NoteDto toDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getEquipment().getId(),
                note.getFact()
        );
    }

    private Note toEntity(NoteInputDto dto) {
        Equipment equipment = equipmentRepository.findById(dto.equipmentId())
                .orElseThrow(() -> new EquipmentNotFoundException(dto.equipmentId()));

        return new Note(equipment, dto.fact());
    }
}

