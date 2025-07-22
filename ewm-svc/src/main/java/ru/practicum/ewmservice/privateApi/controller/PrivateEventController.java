package ru.practicum.ewmservice.privateApi.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.base.dto.event.*;
import ru.practicum.ewmservice.privateApi.service.PrivateEventService;
import ru.practicum.ewmservice.privateApi.service.PrivateRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;
    private final PrivateRequestService requestService;

    @PostMapping
    public ResponseEntity<FullEventDto> createEvent(@RequestBody @Valid AddEventDto dto,
                                                    @PathVariable @Positive Long userId) {
        log.info("POST /users/{}/events", userId);
        FullEventDto result = eventService.createEvent(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ShortEventDto>> getEventsUser(@PathVariable @Positive Long userId,
                                                             @RequestParam(defaultValue = "0")
                                                             @PositiveOrZero Integer from,
                                                             @RequestParam(defaultValue = "10")
                                                             @PositiveOrZero Integer size) {
        log.info("GET /users/{}/events", userId);
        List<ShortEventDto> result = eventService.getEvents(userId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<FullEventDto> getEventById(@PathVariable @Positive Long userId,
                                                     @PathVariable @Positive Long eventId) {
        log.info("GET /users/{}/events/{}", userId, eventId);
        FullEventDto result = eventService.getEventById(userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDto> updateEvent(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid UpdateEventUserDto dto) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        FullEventDto result = eventService.updateEvent(userId, eventId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequest(@PathVariable @Positive Long userId,
                                                                    @PathVariable @Positive Long eventId) {
        log.info("GET /users/{}/requests/{}", userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(requestService.getRequest(userId, eventId));
    }
}
