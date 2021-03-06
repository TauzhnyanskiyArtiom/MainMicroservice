package com.onpu.web.api.controller;

import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    MessageService loggedMessageService;

    UserReadMapper userReadMapper;


    @GetMapping
    public List<MessageReadDto> findForUser(
            @AuthenticationPrincipal OAuth2User oauthUser) {

        return loggedMessageService.findForUser(oauthUser.getUser());
    }

    @GetMapping("{message_id}")
    public MessageReadDto getOne(
            @PathVariable("message_id") Long messageId) {

        return loggedMessageService.findById(messageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public MessageReadDto addMessage(
            @RequestBody MessageCreateDto message,
            @AuthenticationPrincipal OAuth2User oauthUser) {

        UserReadDto user = userReadMapper.map(oauthUser.getUser());
        message.setAuthor(user);

        return loggedMessageService.createMessage(message);
    }

    @PutMapping("{message_id}")
    public MessageReadDto updateMessage(
            @PathVariable("message_id") Long messageId,
            @RequestBody MessageCreateDto message,
            @AuthenticationPrincipal OAuth2User oauthUser) {


        UserReadDto user = userReadMapper.map(oauthUser.getUser());
        message.setAuthor(user);

        return loggedMessageService.updateMessage(messageId, message);
    }

    @DeleteMapping("{message_id}")
    public void deleteMessage(@PathVariable("message_id") Long messageId) {
        if (!loggedMessageService.deleteMessage(messageId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
