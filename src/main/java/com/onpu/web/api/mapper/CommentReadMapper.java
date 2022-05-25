package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentReadMapper implements Mapper<CommentEntity, CommentReadDto> {

    UserReadMapper userReadMapper;

    @Override
    public CommentReadDto map(CommentEntity object) {

        UserReadDto author = userReadMapper.map(object.getAuthor());

        return CommentReadDto.builder()
                .id(object.getId())
                .message(object.getMessage().getId())
                .text(object.getText())
                .author(author)
                .build();
    }
}
