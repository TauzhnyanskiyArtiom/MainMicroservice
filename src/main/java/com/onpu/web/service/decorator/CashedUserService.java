package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CashedUserService implements UserService {

    UserService userServiceImpl;

    @NonFinal
    ConcurrentHashMap<String, UserEntity> cashedUsers = new ConcurrentHashMap<>();


    @Override
    public Optional<UserEntity> findById(String id) {
        if (cashedUsers.containsKey(id)) {
            return Optional.of(cashedUsers.get(id));
        }

        Optional<UserEntity> user = userServiceImpl.findById(id);

        user.ifPresent( u -> cashedUsers.put(u.getId(), u));


        return user;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @Override
    public UserEntity create(UserEntity user) {
        userServiceImpl.create(user);
        cashedUsers.put(user.getId(), user);
        return user;
    }
}