package com.onpu.web.api.controller;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileController {

    UserService loggedUserService;

    SubscriptionService loggedSubscriptionService;

    @GetMapping("{id}")
    public ProfileReadDto getProfile(@PathVariable("id") String userId) {

        final ProfileReadDto profile = loggedUserService.getProfile(userId);
        return profile;
    }

    @PostMapping("change-subscription/{channelId}")
    public ProfileReadDto changeSubscription(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = loggedUserService.getById(channelId);
        UserEntity subscriber = oauthUser.getUser();

        return loggedSubscriptionService.changeSubscription(channel, subscriber);
    }

    @GetMapping("get-subscribers/{channelId}")
    public List<SubscriptionReadDto> subscribers(
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = loggedUserService.getById(channelId);
        return loggedSubscriptionService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    public SubscriptionReadDto changeSubscriptionStatus(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("subscriberId") String subscriberId
    ) {
        UserEntity channel = oauthUser.getUser();
        UserEntity subscriber = loggedUserService.getById(subscriberId);
        return loggedSubscriptionService.changeSubscriptionStatus(channel, subscriber);
    }


}
