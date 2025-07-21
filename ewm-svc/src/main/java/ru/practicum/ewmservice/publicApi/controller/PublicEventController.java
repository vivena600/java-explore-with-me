package ru.practicum.ewmservice.publicApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;
import ru.practicum.ewmservice.publicApi.dto.RequestEventDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
public class PublicEventController {

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
    ResponseEntity<ShortEventDto> getEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false)  List<Long> categories,
                                            @RequestParam(required = false)  Boolean paid,
                                            @RequestParam(required = false)LocalDateTime rangeStart,
                                            @RequestParam(required = false)LocalDateTime rangeEnd,
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
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortEventDto> getEvent(@PathVariable @Positive Long id) {
        return null;
    }
}
