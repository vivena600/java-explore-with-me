package ru.practicum.ewmservice.base.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewmservice.base.enums.StateRequestEvent;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class EventRequestStatusUpdateDto {
    private List<Long> requestIds;
    private StateRequestEvent status;
}
