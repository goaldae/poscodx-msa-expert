package com.poscodx.orderservice.service;

import com.poscodx.orderservice.dto.OrderDto;
import com.poscodx.orderservice.jpa.OrderEntity;
import com.poscodx.orderservice.jpa.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto){
        orderDto.setOrderId(UUID.randomUUID().toString()); //order 시리얼 번호를 랜덤 id값으로 부여함
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());


        //변환 작업을 거쳐
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //딱 맞아덜어지지 않으면 매칭되지 않는 옵션
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        //저장한다
        orderRepository.save(orderEntity);

        OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);
        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
