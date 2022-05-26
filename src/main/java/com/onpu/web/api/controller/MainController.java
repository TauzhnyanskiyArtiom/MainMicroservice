package com.onpu.web.api.controller;


import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

    @NonFinal
    @Value("${spring.profiles.active:prod}")
    String profile;

    UserReadMapper userReadMapper;

    MessageService loggedMessageService;


    @Transactional
    @GetMapping
    public String index(@AuthenticationPrincipal OAuth2User oauthUser,
                        Model model) {
        Map<Object, Object> data = new HashMap<>();

        if (oauthUser != null) {
            UserReadDto userEntity = userReadMapper.map(oauthUser.getUser());
            data.put("profile", userEntity);
            List<MessageReadDto> messages = loggedMessageService.findForUser(oauthUser.getUser());
            data.put("profile", userEntity);
            data.put("messages", messages);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
