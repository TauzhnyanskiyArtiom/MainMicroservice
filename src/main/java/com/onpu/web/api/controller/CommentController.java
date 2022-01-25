package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.entity.CommentEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@RestController
@Transactional
@RequestMapping("/api/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentService loggedCommentService;
    BiConsumer<EventType, CommentEntity> wsSender;

    public CommentController(CommentService loggedCommentService, WsSender wsSender) {
        this.loggedCommentService = loggedCommentService;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public CompletableFuture<CommentEntity> createComment(
            @RequestBody CommentEntity comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {
        return CompletableFuture.supplyAsync(oauthUser::getUser)
                .thenApplyAsync((user) -> {
                    CommentEntity createdComment = loggedCommentService.create(comment, user);
                    wsSender.accept(EventType.CREATE, createdComment);
                    return createdComment;
                });
    }
}
