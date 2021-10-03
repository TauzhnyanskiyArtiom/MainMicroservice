package com.onpu.web.service.implementation;

import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.MessageRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    UserSubscriptionRepository userSubscriptionRepository;


    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {
        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity)
                .stream()
                .filter(UserSubscriptionEntity::isActive)
                .map(UserSubscriptionEntity::getChannel)
                .collect(Collectors.toList());

        channels.add(userEntity);

        List<MessageEntity> messages = messageRepository.findByAuthorIn(channels);
        return messages;
    }

    @Override
    public MessageEntity updateMessage(MessageEntity messageFromDB, MessageEntity message) {

        BeanUtils.copyProperties(message, messageFromDB, "id", "comments","author");
        messageFromDB.setCreationDate(LocalDateTime.now());
        messageRepository.saveAndFlush(messageFromDB);
        return messageFromDB;
    }

    @Override
    public void deleteMessage(MessageEntity message) {
        messageRepository.delete(message);
    }

    @Override
    public MessageEntity createMessage(MessageEntity message, UserEntity user) {

        message.setAuthor(user);
        message.setCreationDate(LocalDateTime.now());
        MessageEntity resultMessage = messageRepository.saveAndFlush(message);
        return resultMessage;
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        List<MessageEntity> messages = optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(() -> messageRepository.findAll());

        return messages;
    }
}
