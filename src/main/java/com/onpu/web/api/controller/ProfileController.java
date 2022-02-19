package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @JsonView(Views.FullProfile.class)
    public UserEntity getProfile(@PathVariable("id") String userId) {
        UserEntity user = loggedUserService.getById(userId);
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public UserEntity changeSubscription(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = loggedUserService.getById(channelId);
        UserEntity subscriber = oauthUser.getUser();
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return loggedSubscriptionService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscriptionEntity> subscribers(
            @PathVariable("channelId") String channelId
    ) {
        UserEntity channel = loggedUserService.getById(channelId);
        return loggedSubscriptionService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscriptionEntity changeSubscriptionStatus(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable("subscriberId") String subscriberId
    ) {
        UserEntity channel = oauthUser.getUser();
        UserEntity subscriber = loggedUserService.getById(subscriberId);
        return loggedSubscriptionService.changeSubscriptionStatus(channel, subscriber);
    }


}
