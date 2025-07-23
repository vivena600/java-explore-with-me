package ru.practicum.ewmservice.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.compilations.AddCompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.base.dto.compilations.UpdateCompilationDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.CompilationMapper;
import ru.practicum.ewmservice.base.model.Compilation;
import ru.practicum.ewmservice.base.model.Event;
import ru.practicum.ewmservice.base.repository.CompilationRepository;
import ru.practicum.ewmservice.base.repository.EventRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    /**
     * Создание подборки событий
     */
    @Override
    public CompilationDto createdCompilations(AddCompilationDto add) {
        log.info("Created compilations: {}", add.toString());
        Compilation entity = compilationMapper.mapAddDto(add, checkEvents(add.getEvents()));
        log.info("Successfully created compilations: {}", entity.toString());
        return compilationMapper.toDto(compilationRepository.save(entity));
    }

    /**
     * Обновление данных о подборке событий
     * (если поле не указано, то оно не изменяется)
     */
    @Override
    public CompilationDto updateCompilation(UpdateCompilationDto compilation, Long compId) {
        log.info("Updated compilation: {} with id {}", compilation.toString(), compId);
        Compilation oldComp = checkCompilation(compId);

        if (compilation.getPinned() != null) {
            oldComp.setPinned(compilation.getPinned());
        }

        if (compilation.getTitle() != null) {
            oldComp.setTitle(compilation.getTitle());
        }

        if (compilation.getEvents() != null) {
            oldComp.setEvents(checkEvents(compilation.getEvents()));
        }

        log.info("Updated compilation: {} with id {}", oldComp.toString(), compId);
        return compilationMapper.toDto(compilationRepository.save(oldComp));
    }


    /**
     * Удаление подборки по его id
     */
    @Override
    public void deleteCompilation(Long compilationId) {
        log.info("Deleted compilation: {}", compilationId);
        Compilation compilation = checkCompilation(compilationId);
        compilationRepository.delete(compilation);
        log.info("Successfully deleted compilation: {}", compilationId);
    }

    private Compilation checkCompilation(Long compilationId) {
        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + compilationId + " was not found"));
    }

    private Set<Event> checkEvents(List<Long> events) {
        return events == null ? Collections.emptySet() :
                new HashSet<>(eventRepository.findAllByIdIn(events));
    }
}
