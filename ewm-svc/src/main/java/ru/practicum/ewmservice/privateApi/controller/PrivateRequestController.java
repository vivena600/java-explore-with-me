package ru.practicum.ewmservice.privateApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;
import ru.practicum.ewmservice.privateApi.service.PrivateRequestService;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {
    private final PrivateRequestService requestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createdRequest(@PathVariable @Positive Long userId,
                                                                  @RequestParam @Positive Long eventId) {
        log.info("GET /users/{}/requests/{}", userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createdRequest(userId, eventId));
    }
}
