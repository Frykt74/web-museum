package com.web.view.dto.location.models;

import com.web.view.dto.base.BaseViewModel;
import com.web.view.dto.theme.models.ThemeViewModel;

public record LocationDetailsViewModel(
        BaseViewModel base,
        LocationViewModel location
) {
}

