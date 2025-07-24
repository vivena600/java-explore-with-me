package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.admin.service.AdminCategoryService;
import ru.practicum.ewmservice.base.dto.category.AddCategoryDto;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid AddCategoryDto categoryDto) {
        log.info("POST /admin/categories");
        return ResponseEntity.status(HttpStatus.CREATED).body(adminCategoryService.createCategory(categoryDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("catId") @Positive Long catId) {
        log.info("DELETE /admin/categories/{}", catId );
        adminCategoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("catId") @Positive Long catId,
                                                      @RequestBody @Valid AddCategoryDto categoryDto) {
        log.info("PATCH /admin/categories/{}", catId );
        return ResponseEntity.ok(adminCategoryService.updateCategory(catId, categoryDto));
    }
}
