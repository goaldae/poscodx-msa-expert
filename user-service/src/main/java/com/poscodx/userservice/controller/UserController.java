package com.poscodx.userservice.controller;

import com.netflix.discovery.converters.Auto;
import com.poscodx.userservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    //yml 파일에 있는 환경 변수를 사용할 때
    private Environment env;
    //필드에 직접 주입하는 것보다 생성자를 통해 주입하는 것이 좋음

    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }

    @Autowired
    private Greeting greeting; //환경변수 받아온 vo 객체를 가져옴 위와 두가지 방식

    @GetMapping("health_check")
    public String status(){
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome(){
        //return env.getProperty("greeting.message"); 환경변수에서 직접 가져오기
        return greeting.getMessage(); //환경변수를 가져온 vo 객체를 가져오기
    }

}
