package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.ValidationService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StatsController {
    private final ValidationService validationService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityResponse<HitDto> saveStats(@RequestBody HitDto dto) {
        log.info("POST /hit");
        return null;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public EntityResponse<List<StatsDto>> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               LocalDateTime start,
                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                LocalDateTime end,
                                                   @RequestParam List<String> uri,
                                                   @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /stats");
        validationService.checkStartAndEndTime(start, end);
        return null;
    }

}
