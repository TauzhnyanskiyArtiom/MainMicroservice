package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(String id);

    UserEntity getById(String id);

    List<UserEntity> getAllUsers();

    UserEntity create(UserEntity user);


}
