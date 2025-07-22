package ru.practicum.ewmservice.publicApi.service;

import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.util.List;

public interface PublicEventService {

    List<ShortEventDto> getEvents(RequestEventDto request);

    FullEventDto getEventById(Long id);
}
