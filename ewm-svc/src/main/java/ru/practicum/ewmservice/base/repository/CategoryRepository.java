package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.base.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
