package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;

public interface CommentService {
    CommentEntity create(CommentEntity comment, UserEntity user);
}
