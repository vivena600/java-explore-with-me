package ru.practicum.ewmservice.privateApi.service;

import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.base.dto.event.UpdateEventUserDto;

import java.util.List;

public interface PrivateEventService {

    FullEventDto createEvent(Long userId, AddEventDto dto);

    List<ShortEventDto> getEvents(Long userId, Integer from, Integer size);

    FullEventDto getEventById(Long userId, Long eventId);

    FullEventDto updateEvent(Long userId, Long eventId, UpdateEventUserDto dto);
}
