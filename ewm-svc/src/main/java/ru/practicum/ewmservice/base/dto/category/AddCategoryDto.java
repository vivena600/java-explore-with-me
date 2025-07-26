package ru.practicum.ewmservice.base.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AddCategoryDto {
    @NotBlank
    @Length(max = 50, min = 1)
    private String name;
}
