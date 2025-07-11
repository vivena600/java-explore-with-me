package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public class StatsServiceImpl implements StatsService {
    @Override
    public HitDto saveStats(HitDto hit) {
        return null;
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        return List.of();
    }
}
