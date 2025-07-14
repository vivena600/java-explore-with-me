package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.module.Hit;
import ru.practicum.module.Stat;

@Mapper(componentModel = "spring")
public interface HitMapper {
    HitDto mapHit(Hit hit);

    Hit mapHitDto(HitDto dto);

    Stat mapStat(StatsDto dto);

    StatsDto mapStatsDto(Stat stat);
}
