package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(String id);

    UserEntity getById(String id);

    UserReadDto getOauthUser(String id);

    ProfileReadDto getProfile(String userId);

    List<UserReadDto> getAllUsers();

    UserEntity create(UserEntity user);

    UserEntity uploadAvatar(MultipartFile image, UserEntity user);

    Optional<byte[]> findAvatar(String id);
}
