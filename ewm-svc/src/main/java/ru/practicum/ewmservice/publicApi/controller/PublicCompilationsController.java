package ru.practicum.ewmservice.publicApi.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.publicApi.service.PublicCompilationsService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationsController {
    private final PublicCompilationsService compilationsService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                                   @RequestParam(defaultValue = "0") Integer from,
                                                                   @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /compilations with param pinned: {}, from: {}, size: {}", pinned, from, size);
        return ResponseEntity.ok().body(compilationsService.getPublicCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilation(@PathVariable @Positive Long compId) {
        log.info("GET /compilation with param compId: {}", compId);
        return ResponseEntity.ok().body(compilationsService.getCompilationById(compId));
    }
}
