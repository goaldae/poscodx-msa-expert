package com.poscodx.userservice.service;

import com.poscodx.userservice.jpa.UserEntity;
import com.poscodx.userservice.jpa.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.stereotype.Service;
import com.poscodx.userservice.dto.UserDto;

import java.util.UUID;

@Service //서비스 빈으로 등록
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto){
        userDto.setUserId(UUID.randomUUID().toString()); //user 시리얼 번호를 랜덤 id값으로 부여함

        //변환 작업을 거쳐
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //딱 맞아덜어지지 않으면 매칭되지 않는 옵션
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd("encrypted_password");

        //저장한다
        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }
}
