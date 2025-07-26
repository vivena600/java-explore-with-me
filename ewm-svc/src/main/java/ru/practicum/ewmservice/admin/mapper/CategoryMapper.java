package ru.practicum.ewmservice.admin.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmservice.base.dto.category.AddCategoryDto;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;
import ru.practicum.ewmservice.base.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category mapAddCategory(AddCategoryDto addCategoryDto);

    CategoryDto mapCategory(Category category);
}
