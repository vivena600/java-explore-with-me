package ru.practicum.ewmservice.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.repository.EventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FullEventDto> getEvents(RequestGetEventsDto eventsDto) {
        log.info("getEvents eventsDto={}", eventsDto);
        List<Event> result = eventRepository.searchEventBuAdminParams(eventsDto);
        return result.stream()
                .map(event -> eventMapper.toFullEventDto(event,
                        userMapper.mapUserShortDto(event.getUserId()),
                        categoryMapper.mapCategory(event.getCategoryId())))
                .toList();
    }
}
