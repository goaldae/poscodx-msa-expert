package com.poscodx.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
@Slf4j
public class SecondServiceController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the second service";
    }
    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header){
        //파라미터로 apigateway-service에서 filter에 등록된 것중에 second-request라고 명명한
        //RequestHeader 값을 가져오겠다
        log.info(header);
        return "Hello world in second service.";
    }
}
