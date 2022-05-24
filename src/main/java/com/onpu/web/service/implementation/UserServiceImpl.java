package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.ProfileReadMapper;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserReadMapper userReadMapper;

    ProfileReadMapper profileReadMapper;


    @Override
    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity getById(String id) {
        return userRepository.getById(id);
    }

    @Override
    public UserReadDto getOauthUser(String id) {
        return findById(id).map(userReadMapper::map).orElse(null);
    }

    @Override
    public ProfileReadDto getProfile(String userId) {
        final UserEntity profile = userRepository.getProfile(userId);
        return profileReadMapper.map(profile);

    }

    @Override
    public List<UserReadDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserEntity create(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }
}
