package ru.practicum.ewmservice.privateApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.admin.mapper.CategoryMapper;
import ru.practicum.ewmservice.admin.mapper.UserMapper;
import ru.practicum.ewmservice.base.dto.category.CategoryDto;
import ru.practicum.ewmservice.base.dto.event.*;
import ru.practicum.ewmservice.base.dto.user.UserShortDto;
import ru.practicum.ewmservice.base.enums.EventState;
import ru.practicum.ewmservice.base.enums.EventStateAction;
import ru.practicum.ewmservice.base.enums.StateRequestEvent;
import ru.practicum.ewmservice.base.enums.StatusEventRequest;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.EventMapper;
import ru.practicum.ewmservice.base.mapper.RequestMapper;
import ru.practicum.ewmservice.base.model.Category;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.Request;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.CategoryRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.base.repository.RequestRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    private final EventMapper eventMapper;

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public FullEventDto createEvent(Long userId, AddEventDto dto) {
        log.info("Отправка запроса на добавление события {}", dto.toString());
        checkEventTime(dto.getEventDate());
        User user = checkUserById(userId);
        Category category = checkCategoryById(dto.getCategory());
        Event event = eventMapper.mapAddEventDto(dto, category);
        event.setCategoryId(category);
        event.setPublishedOn(LocalDateTime.now());
        event.setUserId(user);
        event.setViews(0L);
        UserShortDto userShortDto = userMapper.mapUserShortDto(user);
        CategoryDto categoryDto = categoryMapper.mapCategory(category);
        return eventMapper.toFullEventDto(eventRepository.save(event), userShortDto, categoryDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShortEventDto> getEvents(Long userId, Integer from, Integer size) {
        log.info("отправка запроса на получение событий пользователя с id {}", userId);
        User user = checkUserById(userId);
        UserShortDto shortEventDto = userMapper.mapUserShortDto(user);
        return eventRepository.findAll(PageRequest.of(from, size))
                .stream()
                .map(event -> eventMapper.toShortEventDto(event, shortEventDto,
                        categoryMapper.mapCategory(event.getCategoryId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FullEventDto getEventById(Long userId, Long eventId) {
        log.info("Отправка запроса на получение события по его id {}", eventId);
        Event event = checkEventByIdAndUserId(eventId, userId);
        UserShortDto userShortDto = userMapper.mapUserShortDto(event.getUserId());
        CategoryDto categoryDto = categoryMapper.mapCategory(event.getCategoryId());
        return eventMapper.toFullEventDto(event, userShortDto, categoryDto);
    }

    @Override
    @Transactional
    public FullEventDto updateEvent(Long userId, Long eventId, UpdateEventUserDto dto) {
        User user = checkUserById(userId);
        Event oldEvent = checkEventByIdAndUserId(eventId, userId);
        if (!(oldEvent.getState().equals(EventState.CANCELED) || oldEvent.getState().equals(EventState.PENDING))) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }

        if (dto.getCategoryId() != null) {
            Category category = checkCategoryById(dto.getCategoryId());
            oldEvent.setCategoryId(category);
        }

        if (dto.getStateAction() == EventStateAction.CANCEL_REVIEW) {
            oldEvent.setState(EventState.CANCELED);
        } else {
            oldEvent.setState(EventState.PENDING);
        }

        if (dto.getEventDate() != null) {
            checkEventTime(dto.getEventDate());
            oldEvent.setDate(dto.getEventDate());
        }

        if (dto.getDescription() != null) {
            oldEvent.setDescription(dto.getDescription());
        }
        if (dto.getPaid() != null) {
            oldEvent.setPaid(dto.getPaid());
        }

        if (dto.getAnnotation() != null) {
            oldEvent.setAnnotation(dto.getAnnotation());
        }

        if (dto.getTitle() != null) {
            oldEvent.setTitle(dto.getTitle());
        }

        if (dto.getLocation() != null) {
            oldEvent.setLat(dto.getLocation().getLat());
            oldEvent.setLon(dto.getLocation().getLon());
        }

        if (dto.getParticipantLimit() != null) {
            oldEvent.setParticipantLimit(dto.getParticipantLimit());
        }

        return eventMapper.toFullEventDto(oldEvent,
                userMapper.mapUserShortDto(user),
                categoryMapper.mapCategory(oldEvent.getCategoryId()));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResultDto updateStatusRequestEvent(Long userId, Long eventId,
                                                                      EventRequestStatusUpdateDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id = " + eventId));

        if (event.getConfirmedRequests() != null && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("The participant limit has been reached");
        }

        List<ParticipationRequestDto> confirmedRequests = List.of();
        List<ParticipationRequestDto> rejectedRequests = List.of();

        List<Long> requestId = dto.getRequestIds();
        List<Request> requests = requestRepository.findByRequestId(requestId);
        /*
        Boolean conflictStatus = requests.stream()
                .anyMatch(request -> request.getState().equals(StateRequestEvent.PENDING));
        if (conflictStatus) {
            throw new ConflictException("Request must have status PENDING");
        }

         */

        if (dto.getStatus().equals(StatusEventRequest.CONFIRMED)) {
            confirmedRequests = requests.stream()
                    .peek(request -> {
                        if (event.getConfirmedRequests() != null && event.getConfirmedRequests() >= event.getParticipantLimit()) {
                            throw new ConflictException("The participant limit has been reached");
                        }
                        event.setConfirmedRequests(event.getConfirmedRequests() == null ? 1 :
                                event.getConfirmedRequests() + 1);
                        request.setState(StateRequestEvent.CONFIRMED);
                    })
                    .map(requestMapper::toDto)
                    .toList();

        } else if (dto.getStatus().equals(StatusEventRequest.REJECTED)) {
            rejectedRequests = requests.stream()
                    .peek(request -> request.setState(StateRequestEvent.REJECTED))
                    .map(requestMapper::toDto)
                    .toList();
        }

        EventRequestStatusUpdateResultDto resultDto = EventRequestStatusUpdateResultDto.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
        log.debug("result = {}", resultDto);
        return resultDto;
    }

    private Category checkCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id= " + id + " was not found"));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id= " + id + " was not found"));
    }

    private Event checkEventByIdAndUserId(Long eventId, Long userId) {
        return eventRepository.findByIdAndUserId(userId, eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id = " + eventId +
                        " and userId = " + userId));
    }

    private void checkEventTime(LocalDateTime event) {
        if (event == null && event.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Field: eventDate. Error: the date and time for which the event is scheduled " +
                    "cannot be earlier than two hours from the current moment. Value: " + event);
        }
    }
}
