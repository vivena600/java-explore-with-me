package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.base.dto.CategoryDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.model.Category;
import ru.practicum.ewmservice.base.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategory(int from, int size) {
        log.info("get categories from:{} size:{}", from, size);
        return categoryRepository.findAll(PageRequest.of(from, size))
                .stream()
                .map(categoryMapper::mapCategory)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        log.info("get category by id:{}", id);
        Category result = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=" + id + " was not found"));
        return categoryMapper.mapCategory(result);
    }
}
