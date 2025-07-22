package ru.practicum.ewmservice.privateApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;
import ru.practicum.ewmservice.privateApi.service.PrivateRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateRequestController {
    private final PrivateRequestService requestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createdRequest(@PathVariable @Positive Long userId,
                                                                  @RequestParam @Positive Long eventId) {
        log.info("POST /users/{}/requests/{}", userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createdRequest(userId, eventId));
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(@PathVariable @Positive Long userId) {
        log.info("GET /users/{}/requests", userId);
        return ResponseEntity.ok(requestService.getRequestByUserId(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable @Positive Long userId,
                                                                 @PathVariable @Positive Long requestId) {
        log.info("PATCH /users/{}/cancel", requestId);
        return ResponseEntity.ok(requestService.cancelRequest(userId, requestId));
    }
}
