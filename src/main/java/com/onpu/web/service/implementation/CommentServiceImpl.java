package com.onpu.web.service.implementation;

import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    @Override
    public CommentEntity create(CommentEntity comment, UserEntity user) {
        comment.setAuthor(user);
        commentRepository.save(comment);

        return comment;
    }
}
