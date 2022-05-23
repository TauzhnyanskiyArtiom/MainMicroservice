package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.api.views.Views;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.MetaContentService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.MessageRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
@Service
public class MessageServiceImpl implements MessageService {

    MessageRepository messageRepository;

    MetaContentService metaService;

    BiConsumer<EventType, MessageEntity> wsSender;

    UserSubscriptionRepository userSubscriptionRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MetaContentService metaService, WsSender wsSender, UserSubscriptionRepository userSubscriptionRepository) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {
        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity)
                .stream()
                .filter(UserSubscriptionEntity::isActive)
                .map(UserSubscriptionEntity::getChannel)
                .collect(Collectors.toList());

        channels.add(userEntity);

        return messageRepository.findByAuthorIn(channels, Sort.by("id").descending());
    }


    @Transactional
    @Override
    public Optional<MessageEntity> updateMessage(Long messageId, MessageEntity message) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    BeanUtils.copyProperties(message, messageFromDb, "id", "comments", "author", "createdAt", "modifiedAt");
                    metaService.fillMeta(messageFromDb);
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(messageFromDb -> {
                    wsSender.accept(EventType.UPDATE, messageFromDb);
                    return messageFromDb;
                });
    }

    @Override
    public Optional<MessageEntity> getMessageById(Long messageId) {
        return messageRepository.findById(messageId);
    }


    @Transactional
    @Override
    public boolean deleteMessage(Long messageId) {
        return messageRepository.findById(messageId)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    wsSender.accept(EventType.REMOVE, entity);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public MessageEntity createMessage(MessageEntity message, UserEntity user) {
        message.setAuthor(user);
        metaService.fillMeta(message);
        MessageEntity savedMessage = messageRepository.save(message);

        return savedMessage;
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        return optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(() -> messageRepository.findAll());
    }


}
