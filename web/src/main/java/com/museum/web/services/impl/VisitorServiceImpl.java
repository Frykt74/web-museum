package com.museum.web.services.impl;

import com.museum.web.dtos.visitor.VisitorDto;
import com.museum.web.dtos.visitor.VisitorEditDto;
import com.museum.web.entities.Visitor;
import com.museum.web.exceptions.IllegalVisitorException;
import com.museum.web.exceptions.VisitorNotFoundException;
import com.museum.web.repositories.VisitorRepository;
import com.museum.web.services.RoleService;
import com.museum.web.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl implements VisitorService {
    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository,
                              RoleService roleService) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public VisitorDto findById(Integer id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new VisitorNotFoundException(id));
        return toDto(visitor);
    }

    @Override
    public Page<VisitorDto> findAll(Pageable pageable) {
        return visitorRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public void updateVisitor(VisitorEditDto visitorEditDto, Integer id) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new VisitorNotFoundException(id));

        if (!visitor.getEmail().equals(visitorEditDto.email()) && visitorRepository.existsByEmail(visitorEditDto.email())) {
            throw new IllegalVisitorException("Данный адрес уже используется");
        }

        if (!visitor.getPhone().equals(visitorEditDto.phone()) && visitorRepository.existsByPhone(visitorEditDto.phone())) {
            throw new IllegalVisitorException("Данный телефон уже используется");
        }

        visitor.setSurname(visitorEditDto.surname());
        visitor.setFirstname(visitorEditDto.firstname());
        visitor.setPatronymic(visitorEditDto.patronymic());
        visitor.setEmail(visitorEditDto.email());
        visitor.setPhone(visitorEditDto.phone());

        visitorRepository.save(visitor);
    }

    @Override
    public Integer getVisitorIdByEmail(String email) {
        return visitorRepository.findByEmail(email)
                .orElseThrow(() -> new VisitorNotFoundException("Пользователь с таким почтовым адресом не найден"))
                .getId();
    }

    @Override
    public VisitorDto findByEmail(String email) {
        Visitor visitor = visitorRepository.findByEmail(email)
                .orElseThrow(() -> new VisitorNotFoundException(email));

        return toDto(visitor);
    }

    public void deleteProfile(String email) {
        Visitor visitor = visitorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с данным email не найден"));

        if (visitor.getIsRemoved()) {
            throw new IllegalStateException("Профиль уже удален");
        }

        visitorRepository.markAsRemovedByEmail(email);
    }

    private VisitorDto toDto(Visitor visitor) {
        return new VisitorDto(
                visitor.getId(),
                visitor.getSurname(),
                visitor.getFirstname(),
                visitor.getPatronymic(),
                visitor.getEmail(),
                visitor.getPhone()
        );
    }
}

