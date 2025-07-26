package ru.practicum.ewmservice.base.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmservice.base.dto.compilations.AddCompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.base.model.Compilation;
import ru.practicum.ewmservice.base.model.Event;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    default Compilation mapAddDto(AddCompilationDto addCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .title(addCompilationDto.getTitle())
                .events(events)
                .pinned(addCompilationDto.getPinned())
                .build();
    }

    Compilation toModel(CompilationDto compilationDto);
}
