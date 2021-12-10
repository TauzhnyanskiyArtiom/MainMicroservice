package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedCommentService implements CommentService {

    @Qualifier("commentServiceImpl")
    CommentService commentService;


    @Override
    public CommentEntity create(CommentEntity comment, UserEntity user) {
        log.info("Create comment: ");
        log.info("User id: " + user.getId());
        log.info("Comment text: " + comment.getText());
        log.info("Message id: " + comment.getMessage().getId());


        return commentService.create(comment, user);
    }
}
