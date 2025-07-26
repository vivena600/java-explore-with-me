package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.admin.dto.RequestUpdateEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;

import java.util.List;

public interface AdminEventService {

    List<FullEventDto> getEvents(RequestGetEventsDto eventsDto);

    FullEventDto updateEvent(Long eventId, RequestUpdateEventDto eventDto);
}
