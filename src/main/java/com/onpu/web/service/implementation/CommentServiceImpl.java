package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiConsumer;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    BiConsumer<EventType, CommentEntity> wsSender;

    public CommentServiceImpl(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    @Transactional
    @Override
    public CommentEntity create(CommentEntity comment, UserEntity user) {
        comment.setAuthor(user);
        CommentEntity savedComment = commentRepository.save(comment);
        wsSender.accept(EventType.CREATE, savedComment);
        return savedComment;
    }
}
