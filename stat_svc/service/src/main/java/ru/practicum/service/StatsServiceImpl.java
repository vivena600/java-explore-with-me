package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.module.Hit;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final HitMapper hitMapper;

    @Override
    public HitDto saveStats(HitDto hit) {
        log.info("Сохранение статистики");
        Hit entity = hitMapper.mapHitDto(hit);
        return hitMapper.mapHit(statsRepository.save(entity));
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        return List.of();
    }
}
