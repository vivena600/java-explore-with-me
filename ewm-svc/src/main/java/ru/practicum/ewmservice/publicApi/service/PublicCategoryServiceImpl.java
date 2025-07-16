package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.base.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
}
