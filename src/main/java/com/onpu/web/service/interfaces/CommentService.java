package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.CommentEntity;
import com.onpu.web.store.entity.UserEntity;

import java.util.concurrent.CompletableFuture;

public interface CommentService {
    CompletableFuture<CommentEntity> create(CommentEntity comment, UserEntity user);
}
