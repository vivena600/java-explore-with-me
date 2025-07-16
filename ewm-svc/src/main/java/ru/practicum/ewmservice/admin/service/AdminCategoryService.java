package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.base.dto.AddCategoryDto;
import ru.practicum.ewmservice.base.dto.CategoryDto;

public interface AdminCategoryService {

    CategoryDto createCategory(AddCategoryDto addCategoryDto);

    CategoryDto updateCategory(Long id, AddCategoryDto addCategoryDto);

    void deleteCategory(Long id);
}
