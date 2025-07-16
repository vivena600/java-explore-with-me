package ru.practicum.ewmservice.base.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Getter
@Setter
public class AddCompilationDto {
    private Boolean pinned;

    @UniqueElements
    private List<Integer> events;

    @Length(max = 50, min = 1)
    private String title;
}
