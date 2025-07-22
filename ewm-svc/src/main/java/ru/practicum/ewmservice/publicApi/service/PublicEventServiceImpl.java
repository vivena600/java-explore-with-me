package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ShortEventDto> getEvents(RequestEventDto request) {
        log.info("get events={}", request);
        List<Event> result = eventRepository.searchEventByPublicParam(request);
        return result.stream()
                .map(event -> eventMapper.toShortEventDto(event,
                        userMapper.mapUserShortDto(event.getUserId()),
                        categoryMapper.mapCategory(event.getCategoryId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FullEventDto getEventById(Long id) {
        log.info("get event={}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " is not published"));
        return eventMapper.toFullEventDto(event, userMapper.mapUserShortDto(event.getUserId()),
                categoryMapper.mapCategory(event.getCategoryId()));
    }
}
