package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
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

    UserService userService;

    SubscriptionService subscriptionService;

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public UserEntity getProfile(@PathVariable("id") String userId) {
        UserEntity user = userService.findById(userId);
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public UserEntity changeSubscription(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = userService.findById(channelId);
        UserEntity subscriber = oauthUser.getUser();
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return subscriptionService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscriptionEntity> subscribers(
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = userService.findById(channelId);
        return subscriptionService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscriptionEntity changeSubscriptionStatus(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("subscriberId") String subscriberId
    ) {
        UserEntity channel = oauthUser.getUser();
        UserEntity subscriber = userService.findById(subscriberId);
        return subscriptionService.changeSubscriptionStatus(channel, subscriber);
    }


}
