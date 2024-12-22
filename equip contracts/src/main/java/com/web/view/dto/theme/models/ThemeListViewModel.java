package com.web.view.dto.theme.models;

import com.web.view.dto.base.BaseViewModel;

import java.util.List;

public record ThemeListViewModel(
        BaseViewModel base,
        List<ThemeViewModel> themes,
        Integer totalPages
) {
}