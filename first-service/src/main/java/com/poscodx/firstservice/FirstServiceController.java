package com.poscodx.firstservice;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//http://localhost:8081/welcome
//http://localhost:8081/welcome
@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {

    Environment env;

    @Autowired
    public FirstServiceController(Environment env){
        this.env = env;
    }

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

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());
        return String.format("Hi, there. This is a message from First Service on PORT %s"
                , env.getProperty("local.server.port")); //환경변수에 등록된 정보 가져와라
    }
}
