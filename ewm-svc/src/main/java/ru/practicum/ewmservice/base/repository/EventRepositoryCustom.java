package ru.practicum.ewmservice.base.repository;

import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.base.model.Event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> searchEventBuAdminParams(RequestGetEventsDto params);
}
