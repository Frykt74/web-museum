package com.museum.web.services;

import com.museum.web.dtos.location.LocationDto;
import com.museum.web.dtos.location.LocationEditDto;
import com.museum.web.dtos.location.LocationInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto findById(Integer id);

    Page<LocationDto> findAll(Pageable pageable);

    List<LocationDto> findAll();

    void addLocation(LocationInputDto locationInputDto);

    void updateLocation(LocationEditDto locationEditDto);
}
