package ru.practicum.ewmservice.privateApi.service;

import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;

public interface PrivateEventService {

    public FullEventDto createEvent(Long userId, AddEventDto dto);
}
