package ru.practicum.ewmservice.base.repository;

import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> searchEventBuAdminParams(RequestGetEventsDto params);

    List<Event> searchEventByPublicParam(RequestEventDto params);
}
