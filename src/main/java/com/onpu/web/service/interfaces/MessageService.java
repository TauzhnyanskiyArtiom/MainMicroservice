package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<MessageReadDto> findForUser(UserEntity userEntity);

    Optional<MessageReadDto> updateMessage(Long messageId, MessageCreateDto message);

    Optional<MessageReadDto> getMessageById(Long messageId);

    boolean deleteMessage(Long messageId);

    MessageReadDto createMessage(MessageCreateDto message);

    List<MessageReadDto> getListMessages(Optional<String> optionalPrefixName);

    Optional<MessageEntity> findById(Long messageId);

}
