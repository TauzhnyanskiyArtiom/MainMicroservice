package com.onpu.web.api.controller;

import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {

    UserService loggedUserService;

    @GetMapping
    public List<UserEntity> getAllUsers(@AuthenticationPrincipal OAuth2User oauthUser){
        List<UserEntity> users = loggedUserService.getAllUsers();

        users = users.stream()
                .filter(user -> !user.equals(oauthUser.getUser()))
                .collect(Collectors.toList());

        return users;
    }

}
