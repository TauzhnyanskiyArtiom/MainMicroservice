package com.onpu.web.api.controller;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.CommentService;
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

    UserReadMapper userReadMapper;

    CommentService loggedCommentService;

    @PostMapping
    public CommentReadDto createComment(
            @RequestBody CommentCreateDto comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {

        UserReadDto user = userReadMapper.map(oauthUser.getUser());
        comment.setAuthor(user);
        return loggedCommentService.create(comment);
   }

    @DeleteMapping("{comment_id}")
    public void deleteComment(@PathVariable("comment_id") Long commentId) {
        if (!loggedCommentService.deleteComment(commentId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
