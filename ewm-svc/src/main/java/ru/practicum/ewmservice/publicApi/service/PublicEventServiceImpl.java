package ru.practicum.ewmservice.publicApi.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatClient;
import ru.practicum.dto.HitDto;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.base.enums.EventState;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Import({StatClient.class})
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;
    private final StatClient client;
    private static final String nameApp = "ewm-service";

    @Override
    @Transactional(readOnly = true)
    public List<ShortEventDto> getEvents(RequestEventDto request, HttpServletRequest hitRequest) {
        log.info("get events={}", request);
        List<Event> result = eventRepository.searchEventByPublicParam(request);
        saveEndpointHit(hitRequest);
        return result.stream()
                .map(event -> eventMapper.toShortEventDto(event,
                        userMapper.mapUserShortDto(event.getUserId()),
                        categoryMapper.mapCategory(event.getCategoryId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FullEventDto getEventById(Long id, HttpServletRequest request) {
        log.info("get event={}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " is not published"));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Event with id=" + id + " is not published");
        }
        saveEndpointHit(request);
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return eventMapper.toFullEventDto(event, userMapper.mapUserShortDto(event.getUserId()),
                categoryMapper.mapCategory(event.getCategoryId()));
    }

    private void saveEndpointHit(HttpServletRequest request) {
        HitDto hitDto = HitDto.builder()
                .timestamp(LocalDateTime.now())
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app(nameApp)
                .build();

        client.saveHit(hitDto);
    }
}
