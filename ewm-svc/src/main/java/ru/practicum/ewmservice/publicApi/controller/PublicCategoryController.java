package ru.practicum.ewmservice.publicApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;
import ru.practicum.ewmservice.publicApi.service.PublicCategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicCategoryController {
    private final PublicCategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0") Integer from,
                                                             @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /categories");
        List<CategoryDto> result = service.getCategory(from, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Positive Long catId) {
        log.info("GET /category/{}", catId);
        CategoryDto result = service.getCategoryById(catId);
        return ResponseEntity.ok(result);
    }
}
