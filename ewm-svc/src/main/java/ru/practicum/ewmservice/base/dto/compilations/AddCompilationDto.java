package ru.practicum.ewmservice.base.dto.compilations;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddCompilationDto {
    @Default()
    private Boolean pinned = false;

    private List<Long> events;

    @Length(max = 50, min = 1)
    @NotBlank
    private String title;
}
