package com.web.view.dto.category.models;

import com.web.view.dto.base.BaseViewModel;

public record CategoryDetailsViewModel(
        BaseViewModel base,
        CategoryViewModel category
) {
}
