package com.poscodx.userservice.service;

import com.poscodx.userservice.jpa.UserEntity;
import com.poscodx.userservice.jpa.UserRepository;
import com.poscodx.userservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.poscodx.userservice.dto.UserDto;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service //서비스 빈으로 등록
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    Environment env;
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity userEntity = userRepository.findByEmail(username);

       if(userEntity == null){ //유저가 존재하지 않는다면?
           throw new UsernameNotFoundException(username);
       }

       return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
               true, true, true, true,
               new ArrayList<>());
    }

    @Autowired //생성자로 의존성 주입
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder
    , Environment env, RestTemplate restTemplate){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto){
        userDto.setUserId(UUID.randomUUID().toString()); //user 시리얼 번호를 랜덤 id값으로 부여함

        //변환 작업을 거쳐
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //딱 맞아덜어지지 않으면 매칭되지 않는 옵션
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd())); //여기에 우리가 원하는 암호화 방식을 넣음

        //저장한다
        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UsernameNotFoundException("User not found"); //유저가 없을 때

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        //List<ResponseOrder> orders = new ArrayList<>();
        //Rest call로 다른 service인 order service에서 사용자 주문 정보를 가져와보자
        //String orderUrl = "http://127.0.0.1:8000/order-service/%s/orders";
        String orderUrl = String.format(env.getProperty("order_service.url"), userId); //안에 %s 파라미터 userid로 치환
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null
                , new ParameterizedTypeReference<List<ResponseOrder>>() {

        });

        List<ResponseOrder> ordersList = orderListResponse.getBody();
        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }
}
