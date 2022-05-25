package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(String id);

    UserEntity getById(String id);

    UserReadDto getOauthUser(String id);

    ProfileReadDto getProfile(String userId);

    List<UserReadDto> getAllUsers();

    UserEntity create(UserEntity user);

}
