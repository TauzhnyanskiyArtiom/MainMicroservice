package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
@Transactional
@RequestMapping("/api/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    MessageService loggedMessageService;


    @GetMapping
    @JsonView(Views.FullMessage.class)
    public CompletableFuture<List<MessageEntity>> list(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName){


        return CompletableFuture
                .supplyAsync(() -> loggedMessageService.getListMessages(optionalPrefixName));
    }

    @GetMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public MessageEntity getOne(
            @PathVariable(name = "message_id") MessageEntity message){

        return message;
    }


    @PostMapping
    @JsonView(Views.FullMessage.class)
    public CompletableFuture<MessageEntity> addMessage(
            @RequestBody MessageEntity message,
            @AuthenticationPrincipal OAuth2User oauthUser){

        UserEntity user = oauthUser.getUser();

        return CompletableFuture
                .supplyAsync(() -> loggedMessageService.createMessage(message, user));
    }

    @PutMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public CompletableFuture<MessageEntity> updateMessage(
            @PathVariable("message_id") MessageEntity messageFromDB,
            @RequestBody MessageEntity message){

        return CompletableFuture
                .supplyAsync(() -> loggedMessageService.updateMessage(messageFromDB, message)
                );
    }

    @DeleteMapping("{message_id}")
    public CompletableFuture<Void> deleteMessage( @PathVariable("message_id") MessageEntity message) {
        return CompletableFuture
                .runAsync(() -> loggedMessageService.deleteMessage(message));
    }

}
