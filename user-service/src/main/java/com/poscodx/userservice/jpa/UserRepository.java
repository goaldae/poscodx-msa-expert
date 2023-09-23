package com.poscodx.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
