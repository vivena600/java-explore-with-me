package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.excpection.BadRequestException;

import java.time.LocalDateTime;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void checkStartAndEndTime(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date cannot be after end date");
        }
    }
}
