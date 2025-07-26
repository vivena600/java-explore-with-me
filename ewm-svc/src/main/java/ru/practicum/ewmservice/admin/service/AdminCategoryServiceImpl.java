package ru.practicum.ewmservice.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.base.dto.category.AddCategoryDto;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.model.Category;
import ru.practicum.ewmservice.base.repository.CategoryRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto createCategory(AddCategoryDto addCategoryDto) {
        log.info("created category with name {}", addCategoryDto.getName());
        Category category = categoryMapper.mapAddCategory(addCategoryDto);
        return categoryMapper.mapCategory(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(Long id, AddCategoryDto addCategoryDto) {
        log.info("updated category with id {}", id);
        Category category = checkCategory(id);
        category.setName(addCategoryDto.getName());
        return categoryMapper.mapCategory(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        log.info("deleting category with id {}", id);
        Category category = checkCategory(id);
        checkEventByCategoryId(category.getId());
        categoryRepository.delete(category);
    }

    private Category checkCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=" + id + " was not found"));
    }

    private void checkEventByCategoryId(Long id) {
        if (!eventRepository.findAllByCategoryId(id).isEmpty()) {
            throw new ConflictException("Category with id=" + id + " already exists");
        }
    }
}
