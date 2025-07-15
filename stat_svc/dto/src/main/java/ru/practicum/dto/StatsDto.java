package ru.practicum.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatsDto {
    private String app;
    private String uri;
    private long hits;
}
