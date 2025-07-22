package ru.practicum.ewmservice.privateApi.service;

import org.apache.coyote.BadRequestException;
import ru.practicum.ewmservice.base.dto.event.*;

import java.util.List;

public interface PrivateEventService {

    FullEventDto createEvent(Long userId, AddEventDto dto);

    List<ShortEventDto> getEvents(Long userId, Integer from, Integer size);

    FullEventDto getEventById(Long userId, Long eventId);

    FullEventDto updateEvent(Long userId, Long eventId, UpdateEventUserDto dto);

    EventRequestStatusUpdateResultDto updateStatusRequestEvent(Long userId, Long eventId,
                                                               EventRequestStatusUpdateDto dto);
}
