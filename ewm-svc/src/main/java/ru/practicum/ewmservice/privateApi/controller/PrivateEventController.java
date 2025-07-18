package ru.practicum.ewmservice.privateApi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.privateApi.service.PrivateEventService;

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
}
