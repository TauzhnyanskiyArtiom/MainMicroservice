package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;

public interface CommentService {
    CommentReadDto create(CommentCreateDto comment);

    boolean deleteComment(Long commentId);
}
