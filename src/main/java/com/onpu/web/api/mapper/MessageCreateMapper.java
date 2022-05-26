package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageCreateMapper implements Mapper<MessageCreateDto, MessageEntity> {

    UserService loggedUserService;

    @Override
    public MessageEntity map(MessageCreateDto object) {

        return MessageEntity.builder()
                .text(object.getText())
                .author(getAuthor(object.getAuthorId()))
                .build();
    }

    private UserEntity getAuthor(String authorId) {
        return Optional.of(authorId)
                .map(loggedUserService::getById).orElse(null);
    }

}
