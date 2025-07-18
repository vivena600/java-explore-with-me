package ru.practicum.ewmservice.privateApi.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.privateApi.service.PrivateEventService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;

    @PostMapping
    public ResponseEntity<FullEventDto> createEvent(@RequestBody @Valid AddEventDto dto,
                                                    @PathVariable Long userId) {
        log.info("POST /users/{}/events", userId);
        FullEventDto result = eventService.createEvent(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ShortEventDto>> getEventsUser(@PathVariable @Positive Long userId,
                                                             @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                             @RequestParam(defaultValue = "10") @PositiveOrZero Integer size) {
        log.info("GET /users/{}/events", userId);
        List<ShortEventDto> result = eventService.getEvents(userId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
