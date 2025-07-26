package ru.practicum.ewmservice.publicApi.service;

import ru.practicum.ewmservice.base.dto.category.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategory(int from, int size);

    CategoryDto getCategoryById(Long id);
}
