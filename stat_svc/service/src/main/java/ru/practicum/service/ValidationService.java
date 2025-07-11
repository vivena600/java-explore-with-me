package ru.practicum.service;

import java.time.LocalDateTime;

public interface ValidationService {

    void checkStartAndEndTime(LocalDateTime startDate, LocalDateTime endDate);
}
