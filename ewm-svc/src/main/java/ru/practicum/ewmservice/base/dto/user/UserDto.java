package ru.practicum.ewmservice.base.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
