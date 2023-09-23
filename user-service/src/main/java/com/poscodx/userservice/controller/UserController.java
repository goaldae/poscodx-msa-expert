package com.poscodx.userservice.controller;

import com.netflix.discovery.converters.Auto;
import com.poscodx.userservice.dto.UserDto;
import com.poscodx.userservice.jpa.UserEntity;
import com.poscodx.userservice.service.UserService;
import com.poscodx.userservice.vo.Greeting;
import com.poscodx.userservice.vo.RequestUser;
import com.poscodx.userservice.vo.ResponseOrder;
import com.poscodx.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service/")
public class UserController {
    //yml 파일에 있는 환경 변수를 사용할 때
    //필드에 직접 주입하는 것보다 생성자를 통해 주입하는 것이 좋음
    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @Autowired
    private Greeting greeting; //환경변수 받아온 vo 객체를 가져옴 위와 두가지 방식

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        //return env.getProperty("greeting.message"); 환경변수에서 직접 가져오기
        return greeting.getMessage(); //환경변수를 가져온 vo 객체를 가져오기
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        //return "Create user method is called";
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);//201번 성공 메세지를 반환함
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();

        //가져온 각각의 결과를 메퍼로 responseUser형태로 바꿈
        userList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
