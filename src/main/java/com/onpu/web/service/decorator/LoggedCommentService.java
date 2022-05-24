package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.service.interfaces.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedCommentService implements CommentService {

    CommentService commentServiceImpl;



    @Override
    public CommentReadDto create(CommentCreateDto comment) {
        log.info("Create comment: ");
        log.info("User id: " + comment.getAuthorId());
        log.info("Comment text: " + comment.getText());
        log.info("Message id: " + comment.getMessageId());
        return commentServiceImpl.create(comment);
    }

    @Override
    public boolean deleteMessage(Long commentId) {
        log.info("Comment id for delete:" + commentId);
        return commentServiceImpl.deleteMessage(commentId);
    }
}
