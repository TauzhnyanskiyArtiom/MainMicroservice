package com.onpu.web.api.controller;

import com.onpu.web.service.interfaces.ProfileService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/search/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {
    ProfileService profileService;

    @GetMapping
    public List<UserEntity> getUsers(
            @RequestParam(value = "prefixName", required = false) Optional<String> optionalPrefixName
    ){
        System.out.println();
        List<UserEntity> users = profileService.getUsers(optionalPrefixName);

        return users;
    }
}
