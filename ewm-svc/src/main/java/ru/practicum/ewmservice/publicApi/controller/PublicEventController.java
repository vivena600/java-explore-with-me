package ru.practicum.ewmservice.publicApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.FullEventDto;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;
import ru.practicum.ewmservice.publicApi.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventController {
    private final PublicEventService eventService;

    /***
     * Писк полной информации о событиях
     * @param text - поисковый запрос
     * @param paid
     * @param categories - список id категорий
     * @param rangeStart - дата и время начала диапазона
     * @param rangeEnd - дата и конец времени диапазона
     * @param from - количество значений, которые необходимо пропустить (по умолчанию 0)
     * @param size - количество событий, которых необходимо вывести (по умолчанию 10)
     * @return
     */
    @GetMapping
    ResponseEntity<List<ShortEventDto>> getEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false)  List<Long> categories,
                                            @RequestParam(required = false)  Boolean paid,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeStart,
                                            @RequestParam(required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /event");
        RequestEventDto request = RequestEventDto.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();
        List<ShortEventDto> result = eventService.getEvents(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullEventDto> getEvent(@PathVariable @Positive Long id) {
        log.info("GET /event/{}", id);
        FullEventDto full = eventService.getEventById(id);
        return ResponseEntity.ok(full);
    }
}
