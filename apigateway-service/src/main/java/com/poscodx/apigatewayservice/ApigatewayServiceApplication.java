package com.poscodx.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServiceApplication.class, args);
	}

	@Bean
	public HttpTraceRepository httpTraceRepository() {
		//클라이언트 요청했던 트레이스 정보가 메모리에 담겨서 필요할 때 엔드포인트로 httptrace로 호출하면 해당하는 값을 사용할 수 있게 됨
		return new InMemoryHttpTraceRepository();
	}

}
