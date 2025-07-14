package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.module.Hit;
import ru.practicum.module.Stat;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final HitMapper hitMapper;

    @Override
    public HitDto saveStats(HitDto hit) {
        log.info("Сохранение статистики {}", hit.toString());
        Hit entity = hitMapper.mapHitDto(hit);
        log.info("Mapped entity: {}", entity);
        return hitMapper.mapHit(statsRepository.save(entity));
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        log.info("Получение статистики");
        List<Stat> stats;
        if (uri == null || uri.isEmpty()) {
            stats = unique ? statsRepository.findHitUniqueIp(start, end) :
                    statsRepository.findHitNotUniqueIp(start, end);
        } else {
            stats = unique ? statsRepository.findHitUniqueIpBetweenUri(start, end, uri) :
                    statsRepository.findHitNotUniqueIpBetweenUri(start, end, uri);
        }
        return stats.stream().map(hitMapper::mapStatsDto)
                .collect(Collectors.toList());
    }
}
