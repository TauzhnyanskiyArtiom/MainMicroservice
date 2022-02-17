package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface MessageService {
    List<MessageEntity> findForUser(UserEntity userEntity);

    CompletableFuture<MessageEntity> updateMessage(Long messageId, MessageEntity message);

    CompletableFuture<Void> deleteMessage(Long messageId);

    CompletableFuture<MessageEntity> createMessage(MessageEntity message, UserEntity user);

    List<MessageEntity> getListMessages(Optional<String> optionalPrefixName);

}
