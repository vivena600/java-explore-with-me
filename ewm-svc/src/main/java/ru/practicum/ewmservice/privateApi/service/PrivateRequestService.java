package ru.practicum.ewmservice.privateApi.service;

import ru.practicum.ewmservice.base.dto.event.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {

    List<ParticipationRequestDto> getRequest(Long userId, Long eventId);

    ParticipationRequestDto createdRequest(Long userId, Long eventId);
}
