package com.poscodx.userservice.error;

import com.netflix.discovery.converters.Auto;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    Environment env;

    @Autowired
    public FeignErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch(response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getOrders")){ //아무오류나 다 하는게 아니라 설정한 것만
                    return new ResponseStatusException(HttpStatus.valueOf(response.status())
                            , env.getProperty("order_service.exception.orders_is_empty"));
                }
                break;
            default:
                return new Exception(response.reason()); //위에 해당하는 것이 아니면 그냥 오류 반환
        }

        return null;
    }
}
