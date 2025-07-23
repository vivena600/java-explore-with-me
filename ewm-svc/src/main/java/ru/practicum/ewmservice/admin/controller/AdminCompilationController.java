package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.admin.service.AdminCompilationService;
import ru.practicum.ewmservice.base.dto.compilations.AddCompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.UpdateCompilationDto;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {
    private final AdminCompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> createdCompilation(@RequestBody @Valid AddCompilationDto compilationDto) {
        log.info("POST /admin/compilations with dto = {}", compilationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.createdCompilations(compilationDto));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable @Positive Long compId) {
        log.info("DELETE /admin/compilations/{}", compId);
        compilationService.deleteCompilation(compId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable @Positive Long compId,
                                                            @RequestBody @Valid UpdateCompilationDto updateDto) {
        log.info("PATCH /admin/compilations/{}", compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.updateCompilation(updateDto, compId));
    }
}
