package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<MessageReadDto> findForUser(UserEntity userEntity);

    MessageReadDto updateMessage(Long messageId, MessageCreateDto message);

    boolean deleteMessage(Long messageId);

    MessageReadDto createMessage(MessageCreateDto message);

    Optional<MessageReadDto> findById(Long messageId);

}
