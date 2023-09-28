package com.poscodx.catalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CatalogController {
    @GetMapping("health_check")
    public String status(){
        return "It's Working in User Service";
    }


}
