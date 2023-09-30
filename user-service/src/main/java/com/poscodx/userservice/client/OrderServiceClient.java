package com.poscodx.userservice.client;

import com.poscodx.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @GetMapping("/order-service/{userId}/orders_ng")//order service의 이 엔드포인트를 반환받아오고 싶은 것임
    List<ResponseOrder> getOrders(@PathVariable String userId);

}
