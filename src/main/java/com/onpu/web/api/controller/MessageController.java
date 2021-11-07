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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageController {

    MessageService messageService;


    @GetMapping
    @JsonView(Views.FullMessage.class)
    public ResponseEntity<List<MessageEntity>> list(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName){

        List<MessageEntity> messages = messageService.getListMessages(optionalPrefixName);

        return ResponseEntity.ok(messages);

    }

    @GetMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public ResponseEntity<MessageEntity> getOne(
            @PathVariable(name = "message_id") MessageEntity message){

        return ResponseEntity.ok(message);
    }


    @PostMapping
    @JsonView(Views.FullMessage.class)
    public ResponseEntity<MessageEntity> addMessage(
            @RequestBody MessageEntity message,
            @AuthenticationPrincipal OAuth2User oauthUser){

        UserEntity user = oauthUser.getUser();
        MessageEntity resultMessage = messageService.createMessage(message, user);

        return ResponseEntity.ok(resultMessage);
    }

    @PutMapping("{message_id}")
    @JsonView(Views.FullMessage.class)
    public ResponseEntity<MessageEntity> updateMessage(
            @PathVariable("message_id") MessageEntity messageFromDB,
            @RequestBody MessageEntity message){

        MessageEntity resultMessage = messageService.updateMessage(messageFromDB, message);
        return ResponseEntity.ok(resultMessage);
    }

    @DeleteMapping("{message_id}")
    public void deleteMessage( @PathVariable("message_id") MessageEntity message) {

        messageService.deleteMessage(message);
    }

}
