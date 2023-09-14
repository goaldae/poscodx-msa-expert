package com.poscodx.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {
    //Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);
    //Slf4j 사용하면 위처럼 로거 객체 생성할 필요 없음
    @Override
    public Object run() throws ZuulException { //사용자의 요청이 올 때마다 먼저 실행되는 메소드
        log.info("**************** printing logs:  ");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("**************** printing logs:  " + request.getRequestURI());

        return null;
    }

    @Override
    public String filterType() {
        return "pre"; //사전 필터인지 사후 필터인지 정의

    }

    @Override
    public int filterOrder() {
        return 1;
        //여러개의 필터가 있을 때 순서 정의
    }

    @Override
    public boolean shouldFilter() {
        return true;
        //필터를 사용할 것인가?
    }


}
