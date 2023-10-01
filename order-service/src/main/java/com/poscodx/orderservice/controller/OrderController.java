package com.poscodx.orderservice.controller;

import com.poscodx.orderservice.dto.OrderDto;
import com.poscodx.orderservice.jpa.OrderEntity;
import com.poscodx.orderservice.messagequeue.KafkaProducer;
import com.poscodx.orderservice.service.OrderService;
import com.poscodx.orderservice.vo.RequestOrder;
import com.poscodx.orderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service") //API gateway가 사용하는 prefix
public class OrderController {
    Environment env; //.yml 환경변수 값을 가져온다
    OrderService orderService;
    KafkaProducer kafkaProducer;

    public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
    }


    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    //http://127.0.0.1:0/order-service/{user_id}/orders/
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        /*JPA*/
        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        //위 과정이 끝나면 카프카 토픽에 전달하는 과정을 하면 됨
        /*send this order to the kafka*/
        kafkaProducer.send("example-catalog-topic", orderDto);


        //return "Create user method is called";
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);//201번 성공 메세지를 반환함
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
