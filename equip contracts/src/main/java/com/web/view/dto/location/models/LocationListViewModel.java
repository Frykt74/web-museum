package com.web.view.dto.location.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record LocationListViewModel(
        BaseViewModel base,
        List<LocationViewModel> locations,
        Integer totalPages
) {
}