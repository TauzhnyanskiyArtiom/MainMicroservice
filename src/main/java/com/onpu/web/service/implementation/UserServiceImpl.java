package com.onpu.web.service.implementation;

import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;


    @Override
    public UserEntity findById(String name) {
        return userRepository.findById(name).get();
    }

    @Override
    public List<UserEntity> getUsers(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        List<UserEntity> users = optionalPrefixName
                .map(userRepository::findAllByNameContainingIgnoreCase)
                .orElseGet(() -> new ArrayList<>());

        return users;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
