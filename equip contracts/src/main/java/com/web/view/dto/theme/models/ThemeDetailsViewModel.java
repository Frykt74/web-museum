package com.web.view.dto.theme.models;

import com.web.view.dto.base.BaseViewModel;

public record ThemeDetailsViewModel(
        BaseViewModel base,
        ThemeViewModel theme
) {
}

