package com.onpu.web.api.controller;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;
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

    CommentService loggedCommentService;

    @PostMapping
    public CommentReadDto createComment(
            @RequestBody CommentCreateDto comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {
        comment.setAuthorId(oauthUser.getName());
        final CommentReadDto commentReadDto = loggedCommentService.create(comment);
        return commentReadDto;
   }

    @DeleteMapping("{comment_id}")
    public void deleteMessage(@PathVariable("comment_id") Long commentId) {
        if (!loggedCommentService.deleteMessage(commentId))
            new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
