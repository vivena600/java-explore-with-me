package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.base.dto.compilations.AddCompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.UpdateCompilationDto;

public interface AdminCompilationService {

    CompilationDto createdCompilations(AddCompilationDto add);

    CompilationDto updateCompilation(UpdateCompilationDto compilation, Long compId);

    void deleteCompilation(Long compilationId);
}
