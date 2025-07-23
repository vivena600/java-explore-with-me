package ru.practicum.ewmservice.base.dto.compilations;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCompilationDto {
    @Nullable
    private List<Long> events;

    @Nullable
    private Boolean pinned;

    @Nullable
    @Size(min = 1, max = 50)
    private String title;
}
