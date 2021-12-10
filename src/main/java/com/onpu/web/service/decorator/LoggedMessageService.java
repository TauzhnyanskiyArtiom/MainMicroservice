package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedMessageService implements MessageService{

    @Qualifier("cashedMessageService")
    MessageService messageService;

    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {
        log.info("Messages for user:");
        log.info("User id: " + userEntity.getId());
        log.info("User name: " + userEntity.getName());

        return messageService.findForUser(userEntity);
    }

    @Override
    public MessageEntity updateMessage(MessageEntity messageFromDB, MessageEntity message) {
        log.info("Message id: " + messageFromDB.getId());
        log.info("Message new text: " + message.getText());

        return messageService.updateMessage(messageFromDB, message);

    }

    @Override
    public void deleteMessage(MessageEntity message) {
        log.info("Message id for delete:" + message.getId());

        messageService.deleteMessage(message);
    }

    @Override
    public MessageEntity createMessage(MessageEntity message, UserEntity user) {
        log.info("Create message: ");
        log.info("User id: " + user.getId());
        log.info("Message text: " + message.getText());

        return messageService.createMessage(message, user);
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        log.info("Search messages: " + optionalPrefixName.get());

        return messageService.getListMessages(optionalPrefixName);
    }
}
