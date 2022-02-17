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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
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

    @Async
    @Override
    public CompletableFuture<CommentEntity> create(CommentEntity comment, UserEntity user) {
        final CompletableFuture<CommentEntity> completableFutureComment = CompletableFuture.supplyAsync(() -> {
            comment.setAuthor(user);
            return commentRepository.saveAndFlush(comment);
        }).thenApplyAsync((resultComment) -> {
            wsSender.accept(EventType.CREATE, resultComment);
            return resultComment;
        });
        return completableFutureComment;
    }
}
