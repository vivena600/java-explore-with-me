package ru.practicum.ewmservice.admin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.base.dto.user.AddUserDto;
import ru.practicum.ewmservice.base.dto.user.UserDto;
import ru.practicum.ewmservice.admin.service.AdminUserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<UserDto>> getUser(@RequestParam(required = false) List<Long> ids,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/users");
        List<UserDto> result = userService.getUsers(ids, from, size);
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid AddUserDto userDto) {
        log.info("POST /admin/users");
        UserDto result = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long userId) {
        log.info("DELETE /admin/users/{}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
