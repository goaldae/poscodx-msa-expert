package com.poscodx.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId); //개인적으로 필요한 메소드 정의
}
