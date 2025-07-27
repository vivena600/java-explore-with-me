package ru.practicum.ewmservice.base.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDto {
    @NotBlank
    @Length(min = 1, max = 512)
    private String text;
}
