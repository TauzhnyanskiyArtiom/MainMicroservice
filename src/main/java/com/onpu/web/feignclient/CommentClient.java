package com.onpu.web.feignclient;

import com.onpu.web.api.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "comment", url = "${comment.url}")
public interface CommentClient {

    @PostMapping
    CommentReadDto createComment(CommentCreateDto comment);

    @DeleteMapping("{comment_id}")
    boolean deleteComment(@PathVariable("comment_id") Long commentId);
}
