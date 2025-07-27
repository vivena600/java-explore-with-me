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
import ru.practicum.ewmservice.base.dto.comment.AddCommentDto;
import ru.practicum.ewmservice.base.dto.comment.CommentDto;
import ru.practicum.ewmservice.base.dto.comment.UpdateCommentDto;
import ru.practicum.ewmservice.base.dto.event.AddEventDto;
import ru.practicum.ewmservice.base.dto.event.EventRequestStatusUpdateDto;
import ru.practicum.ewmservice.base.dto.event.EventRequestStatusUpdateResultDto;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.base.dto.event.UpdateEventUserDto;
import ru.practicum.ewmservice.privateApi.service.PrivateCommentService;
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
    private final PrivateCommentService commentService;

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

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResultDto> updateRequestEvent(@PathVariable @Positive Long userId,
                                                     @PathVariable @Positive Long eventId,
                                                     @RequestBody EventRequestStatusUpdateDto dto) {
        log.info("PATCH /users/{}/events/{}/requests", userId, eventId);
        return ResponseEntity.ok(eventService.updateStatusRequestEvent(userId, eventId, dto));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequest(@PathVariable @Positive Long userId,
                                                                    @PathVariable @Positive Long eventId) {
        log.info("GET /users/{}/requests/{}", userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(requestService.getRequest(userId, eventId));
    }

    /**
     * POST /users/{userId}/events/{eventId}/comment
     * Создание комментария
     */
    @PostMapping("/{eventId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @RequestBody @Valid AddCommentDto dto) {
        log.info("POST /users/{}/events/{}/comment", userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(userId, eventId, dto));
    }

    /**
     * PATCH /users/{userId}/events/{eventId}/comment/{commentId}
     * Редактирование комментария
     * Обратите внимание, что комментарий может редактировать только автор комментария
     */
    @PatchMapping("/{eventId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long eventId,
                                                    @PathVariable @Positive Long commentId,
                                                    @RequestBody @Valid UpdateCommentDto dto) {
        log.info("PATCH /users/{}/events/{}/comment", userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(userId, eventId, commentId, dto));
    }

    /**
     * DEL /users/{userId}/events/{eventId}/comment/{commentId}
     * Удаление комментария
     * Обратите внимание, что комментарий может удалять автор комментария, автор события
     * или администратор (/admin/comment/{commentId})
     */
    @DeleteMapping("/{eventId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Long userId,
                                              @PathVariable @Positive Long eventId,
                                              @PathVariable @Positive Long commentId) {
        log.info("DELETE /users/{}/events/{}/comment", userId, eventId);
        commentService.deleteComment(userId, eventId, commentId);
        return ResponseEntity.noContent().build();
    }
}
