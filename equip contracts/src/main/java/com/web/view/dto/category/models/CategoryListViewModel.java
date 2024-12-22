package com.web.view.dto.category.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record CategoryListViewModel(
        BaseViewModel base,
        List<CategoryViewModel> categories,
        Integer totalPages
) {
}
