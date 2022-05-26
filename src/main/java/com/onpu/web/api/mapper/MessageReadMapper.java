package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.MessageEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageReadMapper implements Mapper<MessageEntity, MessageReadDto> {

    UserReadMapper userReadMapper;

    CommentReadMapper commentReadMapper;

    @Override
    public MessageReadDto map(MessageEntity object) {

        UserReadDto author = userReadMapper.map(object.getAuthor());

         List<CommentReadDto> comments = Optional.ofNullable(object.getComments())
                .map(list -> list.stream().map(commentReadMapper::map).collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        return MessageReadDto.builder()
                .id(object.getId())
                .comments(comments)
                .text(object.getText())
                .author(author)
                .link(object.getLink())
                .linkTitle(object.getLinkTitle())
                .linkCover(object.getLinkCover())
                .linkDescription(object.getLinkDescription())
                .build();
    }
}
