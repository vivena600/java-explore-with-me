package ru.practicum.ewmservice.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.admin.dto.RequestUpdateEventDto;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.enums.EventState;
import ru.practicum.ewmservice.base.enums.EventStateActionAdmin;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.model.Category;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.CategoryRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
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

    @Override
    @Transactional
    public FullEventDto updateEvent(Long eventId, RequestUpdateEventDto eventDto) {
        log.info("updateEvent eventDto={}", eventDto.toString());
        Event oldEvent = checkEventById(eventId);

        if (oldEvent.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Cannot publish the event because it's not in the right state: PUBLISHED");
        } else if (oldEvent.getState().equals(EventState.CANCELED)) {
            throw new ConflictException("Cannot publish the event because it's not in the right state: CANCELED");
        } else {
            if (eventDto.getStateAction().equals(EventStateActionAdmin.PUBLISH_EVENT)) {
                oldEvent.setState(EventState.PUBLISHED);
            } else if (eventDto.getStateAction().equals(EventStateActionAdmin.REJECT_EVENT)) {
                oldEvent.setState(EventState.CANCELED);
            }
        }

        checkEventDate(eventDto.getEventDate());
        oldEvent.setPublishedOn(LocalDateTime.now());
        oldEvent.setDate(eventDto.getEventDate());
        oldEvent.setDescription(eventDto.getDescription());
        oldEvent.setTitle(eventDto.getTitle());
        oldEvent.setLon(eventDto.getLocation().getLon());
        oldEvent.setLat(eventDto.getLocation().getLat());
        oldEvent.setParticipantLimit(eventDto.getParticipantLimit());
        oldEvent.setAnnotation(eventDto.getAnnotation());
        oldEvent.setParticipantLimit(eventDto.getParticipantLimit());
        oldEvent.setRequestModeration(eventDto.getRequestModeration());

        Category category = checkCategoryById(eventDto.getCategory());
        oldEvent.setCategoryId(category);

        Event savedEvent = eventRepository.save(oldEvent);
        return eventMapper.toFullEventDto(savedEvent, userMapper.mapUserShortDto(savedEvent.getUserId()),
                categoryMapper.mapCategory(category));
    }

    private Event checkEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private Category checkCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id= " + id + " was not found"));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id= " + id + " was not found"));
    }

    private void checkEventDate(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ConflictException("Field: eventDate. Error: the date and time for which the event is scheduled " +
                    "cannot be earlier than one hours from the current moment. Value: " + eventDate);
        }
    }
}
