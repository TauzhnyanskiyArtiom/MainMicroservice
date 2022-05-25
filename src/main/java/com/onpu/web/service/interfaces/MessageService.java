package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<MessageEntity> findForUser(UserEntity userEntity);

    Optional<MessageEntity> updateMessage(Long messageId, MessageEntity message);

    Optional<MessageEntity> getMessageById(Long messageId);

    boolean deleteMessage(Long messageId);

    MessageEntity createMessage(MessageEntity message, UserEntity user);

    List<MessageEntity> getListMessages(Optional<String> optionalPrefixName);

    Optional<MessageEntity> findById(Long messageId);

}
