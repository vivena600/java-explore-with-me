package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.base.dto.category.AddCategoryDto;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;

public interface AdminCategoryService {

    CategoryDto createCategory(AddCategoryDto addCategoryDto);

    CategoryDto updateCategory(Long id, AddCategoryDto addCategoryDto);

    void deleteCategory(Long id);
}
