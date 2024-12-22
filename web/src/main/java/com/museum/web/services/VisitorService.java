package com.museum.web.services;

import com.museum.web.dtos.visitor.VisitorDto;
import com.museum.web.dtos.visitor.VisitorEditDto;
import com.museum.web.dtos.visitor.VisitorInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitorService {
    VisitorDto findById(Integer id);

    Page<VisitorDto> findAll(Pageable pageable);

    void updateVisitor(VisitorEditDto visitorEditDto, Integer id);

    Integer getVisitorIdByEmail(String email);

    VisitorDto findByEmail(String email);

    void deleteProfile(String email);
}
