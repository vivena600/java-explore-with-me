package ru.practicum.ewmservice.admin.service;

import ru.practicum.ewmservice.base.dto.AddUserDto;
import ru.practicum.ewmservice.base.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto createUser(AddUserDto userDto);

    void deleteUser(Long id);
}
