package com.onpu.web.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
@RequestMapping("/api/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentController {

    CommentService commentService;

    @PostMapping
    @JsonView(Views.FullComment.class)
    public CommentEntity createComment(
            @RequestBody CommentEntity comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {
        UserEntity user = oauthUser.getUser();
        return commentService.create(comment, user);
    }
}
