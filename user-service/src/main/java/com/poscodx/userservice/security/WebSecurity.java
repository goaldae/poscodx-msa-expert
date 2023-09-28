package com.poscodx.userservice.security;

import com.poscodx.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;


@Configuration //설정 빈
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.authorizeRequests().antMatchers("/users/**").permitAll(); //해당 url은 모든 권한이 다 접근할 수 있다
        http.authorizeRequests().antMatchers("/**") //모든 요청에 대해서 그냥 통과시키지 않을 것임
                .hasIpAddress("172.30.1.84") //<-IP 변경해야함, 사용자는 ip를 제한적으로 받을 것임
                .and()
                .addFilter(getAuthenticaionFilter()); //그리고 이 필터를 통과시킨 데이터(로그인 정보)에 한해서만 권한을 부여할 것임

        http.headers().frameOptions().disable(); //h2콘솔 프레임 문제 없애기
    }

    //위 config 메소드와 같이 오버로딩 해서 사용
    //위는 권한에 관한것, 이것은 인증에 관한 것
    //select pwd from users where email=?
    // db_pwd(encrypted) == input_pwd(encrypted) 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);//사용자 검색 비즈니스 로직
        //userDetailsService라는 메소드는 완벽히(?) 구현된 userService를 매개변수로 필요로 해서
        //userService로 가서 UserDetailsService를 상속받고 userServiceImpl에서 UserDetails라는 메소드를 구현해야함
    }

    private AuthenticationFilter getAuthenticaionFilter() throws Exception {
        //우리가 만든 AuthenticationFilter를 필터로 사용할 것이고
        //이는 filter를 상속받고 있어서 상관없음
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }


}
