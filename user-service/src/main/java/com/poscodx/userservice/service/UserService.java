package com.poscodx.userservice.service;

import com.poscodx.userservice.dto.UserDto;
import com.poscodx.userservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
