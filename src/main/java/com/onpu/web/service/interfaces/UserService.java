package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findById(String id);

    List<UserEntity> getAllUsers();
}
