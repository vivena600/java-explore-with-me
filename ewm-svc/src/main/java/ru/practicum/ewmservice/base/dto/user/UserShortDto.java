package ru.practicum.ewmservice.base.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserShortDto {
    private Long id;

    @NotBlank
    private String name;
}
