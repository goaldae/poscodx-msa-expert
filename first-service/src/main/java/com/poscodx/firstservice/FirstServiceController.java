package com.poscodx.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:8081/welcome
//http://localhost:8081/welcome
@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the first service";
    }
    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        //파라미터로 apigateway-service에서 filter에 등록된 것중에 first-request라고 명명한
        //RequestHeader 값을 가져오겠다
        log.info(header);
        return "Hello world in first service.";
    }
}
