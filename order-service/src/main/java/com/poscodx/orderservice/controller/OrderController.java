package com.poscodx.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {
    @GetMapping("health_check")
    public String status(){
        return "It's Working in Order Service";
    }


}
