package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentCreateDto;
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
public class CommentCreateMapper implements Mapper<CommentCreateDto, CommentEntity> {

    UserService loggedUserService;

    MessageService loggedMessageService;

    @Override
    public CommentEntity map(CommentCreateDto object) {
        getMessage(object.getMessageId());


        return CommentEntity.builder()
                .text(object.getText())
                .message(getMessage(object.getMessageId()))
                .author(getAuthor(object.getAuthorId()))
                .build();
    }

    private UserEntity getAuthor(String authorId) {
        return Optional.of(authorId)
                .map(loggedUserService::getById).orElse(null);
    }

    private MessageEntity getMessage(Long messageId) {
        return Optional.of(messageId)
                .flatMap(loggedMessageService::findById).orElse(null);
    }
}
