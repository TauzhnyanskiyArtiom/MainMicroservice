package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedUserService implements UserService {

    @Qualifier("userServiceImpl")
    UserService userService;

    @Override
    public UserEntity findById(String id) {

        log.info("Find User by id: " + id);

        return userService.findById(id);
    }

    @Override
    public List<UserEntity> getUsers(Optional<String> optionalPrefixName) {
        log.info("Search users: " + optionalPrefixName.get());

        return userService.getUsers(optionalPrefixName);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Search all users");
        return userService.getAllUsers();
    }
}
