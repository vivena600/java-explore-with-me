package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.StatsService;
import ru.practicum.service.ValidationService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StatsController {
    private final ValidationService validationService;
    private final StatsService statsService;

    /**
     * POST /hit - Запрос на сохранение информации о том, что к эндпоинту был запрос
     * @param dto
     * @return
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HitDto> saveStats(@RequestBody HitDto dto) {
        log.info("POST /hit");
        HitDto result = statsService.saveStats(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * Get /stats - запрос на получение статистики по посещениям
     * @param start - Дата и время начала диапазона
     * @param end - Дата и время конца диапазона
     * @param uri - Список uri, для которых нужно выгрузить статистику
     * @param unique - Учитывать ли только уникальные посещения
     */
    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StatsDto>> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               LocalDateTime start,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                LocalDateTime end,
                                                   @RequestParam List<String> uri,
                                                   @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /stats");
        validationService.checkStartAndEndTime(start, end);
        List<StatsDto> result = statsService.getStats(start, end, uri, unique);
        return ResponseEntity.ok(result);
    }
}
