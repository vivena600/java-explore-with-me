package ru.practicum.ewmservice.admin.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewmservice.base.dto.user.AddUserDto;
import ru.practicum.ewmservice.base.dto.user.UserDto;
import ru.practicum.ewmservice.base.dto.user.UserShortDto;
import ru.practicum.ewmservice.base.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUser(User user);

    User mapUserDto(UserDto userDto);

    User mapAddUser(AddUserDto addUserDto);

    UserShortDto mapUserShortDto(User user);
}
