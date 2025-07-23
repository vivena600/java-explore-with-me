package ru.practicum.ewmservice.base.dto.compilations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewmservice.base.dto.event.ShortEventDto;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Long id;

    private Set<ShortEventDto> events;

    private Boolean pinned;

    private String title;
}
