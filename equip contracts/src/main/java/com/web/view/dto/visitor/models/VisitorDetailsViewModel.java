package com.web.view.dto.visitor.models;

import com.web.view.dto.base.BaseViewModel;

public record VisitorDetailsViewModel(
        BaseViewModel base,
        VisitorViewModel visitor
) {
}
