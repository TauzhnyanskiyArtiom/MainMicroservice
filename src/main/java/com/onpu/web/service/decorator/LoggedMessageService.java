package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedMessageService implements MessageService{

    MessageService messageServiceImpl;

    @Override
    public List<MessageReadDto> findForUser(UserEntity userEntity) {
        log.info("Messages for user:");
        log.info("User id: " + userEntity.getId());
        log.info("User name: " + userEntity.getName());

        return messageServiceImpl.findForUser(userEntity);
    }

    @Override
    public MessageReadDto updateMessage(Long messageId, MessageCreateDto message) {
        log.info("Message id: " + messageId);
        log.info("Message new text: " + message.getText());

        return messageServiceImpl.updateMessage(messageId, message);

    }


    @Override
    public boolean deleteMessage(Long messageId) {
        log.info("Message id for delete:" + messageId);

        return messageServiceImpl.deleteMessage(messageId);
    }

    @Override
    public MessageReadDto createMessage(MessageCreateDto message) {
        log.info("Create message: ");
        log.info("User id: " + message.getAuthor().getId());
        log.info("Message text: " + message.getText());

        return messageServiceImpl.createMessage(message);
    }


    @Override
    public Optional<MessageReadDto> findById(Long messageId) {
        log.info("Find Message by id: " + messageId);

        return messageServiceImpl.findById(messageId);
    }
}
