package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedMessageService implements MessageService{

    MessageService messageServiceImpl;

    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {
        log.info("Messages for user:");
        log.info("User id: " + userEntity.getId());
        log.info("User name: " + userEntity.getName());

        return messageServiceImpl.findForUser(userEntity);
    }

    @Override
    public CompletableFuture<MessageEntity> updateMessage(Long messageId, MessageEntity message) {
        log.info("Message id: " + messageId);
        log.info("Message new text: " + message.getText());

        return messageServiceImpl.updateMessage(messageId, message);

    }

    @Override
    public CompletableFuture<Void> deleteMessage(Long messageId) {
        log.info("Message id for delete:" + messageId);

        messageServiceImpl.deleteMessage(messageId);
        return null;
    }

    @Override
    public CompletableFuture<MessageEntity> createMessage(MessageEntity message, UserEntity user) {
        log.info("Create message: ");
        log.info("User id: " + user.getId());
        log.info("Message text: " + message.getText());

        return messageServiceImpl.createMessage(message, user);
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        log.info("Search messages: " + optionalPrefixName.get());

        return messageServiceImpl.getListMessages(optionalPrefixName);
    }
}
