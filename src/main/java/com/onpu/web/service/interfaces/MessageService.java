package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<MessageEntity> findForUser(UserEntity userEntity);

    MessageEntity updateMessage(Long messageId, MessageEntity message);

    void deleteMessage(Long messageId);

    MessageEntity createMessage(MessageEntity message, UserEntity user);

    List<MessageEntity> getListMessages(Optional<String> optionalPrefixName);

}
