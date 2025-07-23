package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.admin.dto.RequestGetEventsDto;
import ru.practicum.ewmservice.admin.dto.RequestUpdateEventDto;
import ru.practicum.ewmservice.admin.service.AdminEventService;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@Slf4j
@RequiredArgsConstructor
public class AdminEventController {
    private final AdminEventService eventService;

    /***
     * Писк полной информации о событиях
     * @param users - список id пользователей
     * @param states - список состояний
     * @param categories - список id категорий
     * @param rangeStart - дата и время начала диапазона
     * @param rangeEnd - дата и конец времени диапазона
     * @param from - количество значений, которые необходимо пропустить (по умолчанию 0)
     * @param size - количество событий, которых необходимо вывести (по умолчанию 10)
     * @return
     */
    @GetMapping
    public ResponseEntity<List<FullEventDto>> searchEvents(@RequestParam(required = false) List<Long> users,
                                                           @RequestParam(required = false) List<String> states,
                                                           @RequestParam(required = false) List<Long> categories,
                                                           @RequestParam(required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                               LocalDateTime rangeStart,
                                                           @RequestParam(required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                               LocalDateTime rangeEnd,
                                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("GET /admin/events");
        RequestGetEventsDto param = RequestGetEventsDto.builder()
                .users(users)
                .categories(categories)
                .states(states)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();
        List<FullEventDto> result = eventService.getEvents(param);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<FullEventDto> updateEvent(@PathVariable Long eventId,
                                    @RequestBody @Valid RequestUpdateEventDto eventDto) {
        log.info("PATCH /admin/events/{}", eventId);
        FullEventDto result = eventService.updateEvent(eventId, eventDto);
        return ResponseEntity.ok().body(result);
    }
}
