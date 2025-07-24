package ru.practicum.ewmservice.privateApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;
import ru.practicum.ewmservice.base.enums.EventState;
import ru.practicum.ewmservice.base.enums.StateRequestEvent;
import ru.practicum.ewmservice.base.exception.ConflictException;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.RequestMapper;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.model.Request;
import ru.practicum.ewmservice.base.model.User;
import ru.practicum.ewmservice.base.repository.EventRepository;
import ru.practicum.ewmservice.base.repository.RequestRepository;
import ru.practicum.ewmservice.base.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final RequestMapper requestMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequest(Long userId, Long eventId) {
        Event event = checkEventById(eventId);
        User user = checkUserById(userId);

        List<Request> requests = requestRepository.findByEventId(eventId);
        return requests.stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getRequestByUserId(Long userId) {
        User user = checkUserById(userId);
        List<Request> requests = requestRepository.findByUserId(userId);
        return requests.stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ParticipationRequestDto createdRequest(Long userId, Long eventId) {
        Event event = checkEventById(eventId);
        User user = checkUserById(userId);
        checkUniqueRequest(userId, eventId);

        if (event.getUserId().getId().equals(user.getId())) {
            throw new ConflictException("Event initiator can't created request to participate in your event");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("You cannot participate in an unpublished event");
        }

        if (event.getConfirmedRequests() != null && event.getParticipantLimit() != 0 && event.getParticipantLimit()
                <= event.getConfirmedRequests()) {
            throw new ConflictException("Event participant limit exceeded");
        }

        ParticipationRequestDto createdRequest = ParticipationRequestDto.builder()
                .created(LocalDateTime.now())
                .status((!event.getRequestModeration() || event.getParticipantLimit() == 0)
                        ? StateRequestEvent.CONFIRMED : StateRequestEvent.PENDING)
                .requester(user.getId())
                .event(event.getId())
                .build();

        //вычисление ConfirmedRequest
        if (event.getConfirmedRequests() == null) {
            event.setConfirmedRequests(0L);
        }
        event.setConfirmedRequests(createdRequest.getStatus() == StateRequestEvent.CONFIRMED
                ? event.getConfirmedRequests() + 1 : event.getConfirmedRequests());
        eventRepository.save(event);

        log.info("Created request {}", createdRequest);

        return requestMapper.toDto(requestRepository.save(requestMapper.fromDto(createdRequest, user, event)));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        log.info("Cancel Request with id=" + requestId);
        checkUserById(userId);
        Request request = checkRequestById(requestId);
        checkUniqueRequest(userId, requestId);

        request.setState(StateRequestEvent.CANCELED);
        Request updatedRequest = requestRepository.save(request);
        return requestMapper.toDto(updatedRequest);
    }

    private Event checkEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private User checkUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id= " + userId + " was not found"));
    }

    private Request checkRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id= " + requestId + " was not found"));
    }


    private void checkUniqueRequest(Long userId, Long eventId) {
        List<Request> requests = requestRepository.findByEventIdAndUserId(eventId, userId);
        if (!requests.isEmpty()) {
            throw new ConflictException("Request with id=" + eventId + " already exists");
        }
    }
}
