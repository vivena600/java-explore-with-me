package ru.practicum.ewmservice.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.base.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
