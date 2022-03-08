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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    MessageService loggedMessageService;


    @GetMapping
    @JsonView(Views.FullMessage.class)
    public List<MessageEntity> findForUser(
            @AuthenticationPrincipal OAuth2User oauthUser){

        return loggedMessageService.findForUser(oauthUser.getUser());
    }

    @GetMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public MessageEntity getOne(
            @PathVariable(name = "message_id") MessageEntity message){

        return message;
    }


    @PostMapping
    @JsonView(Views.FullMessage.class)
    public MessageEntity addMessage(
            @RequestBody MessageEntity message,
            @AuthenticationPrincipal OAuth2User oauthUser){

        UserEntity user = oauthUser.getUser();

        return loggedMessageService.createMessage(message, user);
    }

    @PutMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public MessageEntity updateMessage(
            @PathVariable("message_id") Long messageId,
            @RequestBody MessageEntity message){


        return loggedMessageService.updateMessage(messageId, message);
    }

    @DeleteMapping("{message_id}")
    public void deleteMessage( @PathVariable("message_id") Long messageId) {
        loggedMessageService.deleteMessage(messageId);
    }

}
