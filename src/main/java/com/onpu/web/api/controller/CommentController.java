package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentService loggedCommentService;

    @PostMapping
    @JsonView(Views.FullComment.class)
    public CommentEntity createComment(
            @RequestBody CommentEntity comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {

        UserEntity user = oauthUser.getUser();
        return loggedCommentService.create(comment, user);
    }

    @DeleteMapping("{comment_id}")
    public void deleteMessage(@PathVariable("comment_id") Long commentId) {
        if (!loggedCommentService.deleteMessage(commentId))
            new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
