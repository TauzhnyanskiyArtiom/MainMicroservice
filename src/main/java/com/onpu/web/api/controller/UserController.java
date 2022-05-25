package com.onpu.web.api.controller;

import com.onpu.web.api.dto.UserReadDto;
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
    public List<UserReadDto> getAllUsers(@AuthenticationPrincipal OAuth2User oauthUser){
        return loggedUserService.getAllUsers().stream()
                .filter(user -> !user.getId().equals(oauthUser.getName()))
                .collect(Collectors.toList());
    }

}
