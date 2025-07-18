package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ShortEventDto> getEvents(RequestEventDto request) {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public ShortEventDto getEventById(Long id) {
        return null;
    }
}
