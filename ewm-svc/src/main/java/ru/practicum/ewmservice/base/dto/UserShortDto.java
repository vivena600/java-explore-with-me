package ru.practicum.ewmservice.base.dto;

import jakarta.validation.constraints.NotBlank;

public class UserShortDto {
    private Long id;

    @NotBlank
    private String name;
}
