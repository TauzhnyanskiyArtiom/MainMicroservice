package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.ProfileReadMapper;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.service.interfaces.ImageService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    ImageService imageService;

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
        return userReadMapper.map(getById(id));
    }

    @Override
    public ProfileReadDto getProfile(String userId) {
        return profileReadMapper.map(getById(userId));
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

    @SneakyThrows
    @Transactional
    @Override
    public UserEntity uploadAvatar(MultipartFile image, UserEntity user) {
        final String randomNameImage = UUID.randomUUID().toString();
        imageService.upload(randomNameImage, image.getInputStream());
        user.setUserpic(randomNameImage);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<byte[]> findAvatar(String id) {
        return userRepository.findById(id)
                .map(UserEntity::getUserpic)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
