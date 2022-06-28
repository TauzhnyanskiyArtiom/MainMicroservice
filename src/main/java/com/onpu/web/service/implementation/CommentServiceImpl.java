package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.feignclient.CommentClient;
import com.onpu.web.service.interfaces.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    CommentClient commentClient;

    @Override
    public CommentReadDto create(CommentCreateDto comment) {
        return commentClient.createComment(comment);
    }

    @Override
    public boolean deleteComment(Long commentId) {
        return commentClient.deleteComment(commentId);
    }
}
