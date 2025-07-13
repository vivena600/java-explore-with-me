package ru.practicum.dto;

import org.springframework.stereotype.Component;

@Component
public class StatsDto {
    private String app;
    private String uri;
    private Integer hits;
}
