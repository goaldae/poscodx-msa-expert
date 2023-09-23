package com.poscodx.userservice.dto;


import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;

    //하단부터는 Request VO에는 없는 것들
    private String userId;
    private Date createdAt;

    private String encryptedPwd; //암호화 비밀번호
}
