package com.poscodx.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.poscodx.catalogservice.jpa.CatalogEntity;
import com.poscodx.catalogservice.jpa.CatalogRepository;
import com.poscodx.catalogservice.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    CatalogRepository repository;

    @Autowired
    public KafkaConsumer(CatalogRepository repository){
        this.repository = repository;
    }

    @KafkaListener(topics = "example-catalog-topic") //받을 토픽의 이름
    public void updateQty(String kafkaMessage) { //토픽에 데이터가 생기면 이 메소드가 가져옴
        log.info("Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map= mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
            //카프카에서 받은걸 우리가 가진 데이타 타입으로 바꿈
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
        if(entity != null){ //해당 물품이 검색되었을 때
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            repository.save(entity);
        }
    }
}
