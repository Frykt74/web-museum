package com.web.view.dto.visitor.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record VisitorListViewModel(
        BaseViewModel base,
        List<VisitorViewModel> visitor,
        Integer totalPages
) {
}
