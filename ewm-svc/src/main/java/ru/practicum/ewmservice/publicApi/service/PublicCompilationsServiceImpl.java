package ru.practicum.ewmservice.publicApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.base.dto.compilations.CompilationDto;
import ru.practicum.ewmservice.base.exception.NotFoundException;
import ru.practicum.ewmservice.base.mapper.CompilationMapper;
import ru.practicum.ewmservice.base.model.Compilation;
import ru.practicum.ewmservice.base.repository.CompilationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PublicCompilationsServiceImpl implements PublicCompilationsService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getPublicCompilations(Boolean pinned, Integer from, Integer size) {
        log.info("get List<CompilationDto> pinned={}, from={}, size={}", pinned, from, size);
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(PageRequest.of(from, size,
                    Sort.by(Direction.DESC, "id"))).toList();
        } else {
            compilations = compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size,
                    Sort.by(Direction.DESC, "id")));
        }
        log.info("get List<CompilationDto> compilations={}", compilations);
        return compilations.stream().map(compilationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long id) {
        log.info("get compilation by id: {}", id);
        Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + id + " was not found"));
        return compilationMapper.toDto(compilation);
    }
}
