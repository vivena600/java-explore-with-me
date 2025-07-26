package ru.practicum.ewmservice.publicApi.service;

import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> getPublicCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long id);
}
