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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

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

    @PostMapping("change-photo")
    public void changePhoto(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @RequestParam MultipartFile image
    ) {
        if (image.isEmpty())
            return;

        UserEntity user = oauthUser.getUser();
        loggedUserService.uploadAvatar(image, user);
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") String id) {
        return loggedUserService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }


}
