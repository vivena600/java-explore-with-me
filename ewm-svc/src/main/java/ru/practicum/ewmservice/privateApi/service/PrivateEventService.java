package ru.practicum.ewmservice.privateApi.service;

import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;

import java.util.List;

public interface PrivateEventService {

    FullEventDto createEvent(Long userId, AddEventDto dto);

    List<ShortEventDto> getEvents(Long userId, Integer from, Integer size);

    FullEventDto getEventById(Long userId, Long eventId);
}
