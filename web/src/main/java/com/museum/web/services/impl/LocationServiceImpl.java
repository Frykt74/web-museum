package com.museum.web.services.impl;

import com.museum.web.dtos.location.LocationDto;
import com.museum.web.dtos.location.LocationEditDto;
import com.museum.web.dtos.location.LocationInputDto;
import com.museum.web.entities.Location;
import com.museum.web.exceptions.LocationNotFoundException;
import com.museum.web.repositories.LocationRepository;
import com.museum.web.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationDto findById(Integer id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        return toDto(location);
    }

    @Override
    @Cacheable(value = "pagesOfLocations", key = "#pageable.pageNumber")
    public Page<LocationDto> findAll(Pageable pageable) {
        Page<Location> page = locationRepository.findAll(pageable);
        return page.map(this::toDto);
    }

    @Override
    @Cacheable("locations")
    public List<LocationDto> findAll() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "pagesOfLocations", allEntries = true)
    public void addLocation(LocationInputDto locationInputDto) {
        Location location = toEntity(locationInputDto);
        locationRepository.save(location);
    }

    @Override
    @CachePut(value = "pagesOfLocations", key = "#locationEditDto.id")
    public void updateLocation(LocationEditDto locationEditDto) {
        Location location = locationRepository.findById(locationEditDto.id())
                .orElseThrow(() -> new LocationNotFoundException(locationEditDto.id()));
        location.setName(locationEditDto.name());
        locationRepository.save(location);
    }

    private LocationDto toDto(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getTicketPrice());
    }

    private Location toEntity(LocationInputDto dto) {
        return new Location(dto.name(), dto.ticketPrice());
    }
}
