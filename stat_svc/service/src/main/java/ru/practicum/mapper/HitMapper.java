package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.HitDto;
import ru.practicum.module.Hit;

@Mapper
public interface HitMapper {
    HitDto mapHit(Hit hit);

    Hit mapHitDto(HitDto dto);
}
