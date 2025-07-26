package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.base.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
