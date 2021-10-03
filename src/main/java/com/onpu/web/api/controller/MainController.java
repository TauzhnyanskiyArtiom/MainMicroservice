package com.onpu.web.api.controller;


import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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

    UserRepository userRepository;
    MessageService messageService;

    @GetMapping
    public String index(@AuthenticationPrincipal OAuth2User oauthUser,
                        Model model) {
        Map<Object, Object> data = new HashMap<>();

        if (oauthUser != null) {
            UserEntity userEntity = userRepository.findById(oauthUser.getName()).get();

            data.put("profile", userEntity);
            List<MessageEntity> messages = messageService.findForUser(userEntity);
            data.put("messages", messages);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
