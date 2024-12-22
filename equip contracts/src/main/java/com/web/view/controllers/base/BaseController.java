package com.web.view.controllers.base;

import com.web.view.dto.base.BaseViewModel;

public interface BaseController {
    /**
     * Создает базовую модель представления.
     * @param title заголовок страницы
     * @return базовая модель с общими данными
     */
    BaseViewModel createBaseViewModel(String title);
}
