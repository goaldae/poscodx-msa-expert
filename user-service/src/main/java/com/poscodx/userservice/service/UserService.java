package com.poscodx.userservice.service;

import com.poscodx.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
