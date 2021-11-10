package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity findById(String name);

    List<UserEntity> getUsers(Optional<String> optionalPrefixName);

    List<UserEntity> getAllUsers();
}
