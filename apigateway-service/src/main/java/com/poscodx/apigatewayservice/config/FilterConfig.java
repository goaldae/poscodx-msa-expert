package com.poscodx.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//어노테이션만 빼줘도 인식 안됨
public class FilterConfig {
  //  @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f->f.addRequestHeader("first-request","first-request-header")
                                     .addResponseHeader("first-response","first-response-header"))
                        .uri("http://localhost:8081"))

                .route(r -> r.path("/second-service/**")
                        .filters(f->f.addRequestHeader("second-request","second-request-header")
                                .addResponseHeader("second-response","second-response-header"))
                        .uri("http://localhost:8082"))

                .build(); //라우트에 필요했던 내용을 실제 메모리에 반영하는 것
    }
}
