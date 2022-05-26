package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.mapper.MessageCreateMapper;
import com.onpu.web.api.mapper.MessageReadMapper;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.MetaContentService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
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

    MetaContentService metaService;

    MessageReadMapper messageReadMapper;

    MessageCreateMapper messageCreateMapper;

    MessageRepository messageRepository;

    BiConsumer<EventType, MessageEntity> wsSender;

    UserSubscriptionRepository userSubscriptionRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MetaContentService metaService, MessageReadMapper messageReadMapper, MessageCreateMapper messageCreateMapper, WsSender wsSender, UserSubscriptionRepository userSubscriptionRepository) {
        this.messageRepository = messageRepository;
        this.metaService = metaService;
        this.messageReadMapper = messageReadMapper;
        this.messageCreateMapper = messageCreateMapper;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE);
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
    public List<MessageReadDto> findForUser(UserEntity userEntity) {
        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity.getId());
        channels.add(userEntity);
        List<MessageEntity> messages = messageRepository.findByAuthorIn(channels, Sort.by("id").descending());
        return messages.stream().map(messageReadMapper::map).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public Optional<MessageReadDto> updateMessage(Long messageId, MessageCreateDto message) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    messageFromDb.setText(message.getText());
                    metaService.fillMeta(messageFromDb);
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(messageFromDb -> {
                    wsSender.accept(EventType.UPDATE, messageFromDb);
                    return messageFromDb;
                }).map(messageReadMapper::map);
    }

    @Override
    public Optional<MessageReadDto> getMessageById(Long messageId) {
        return messageRepository.findById(messageId).map(messageReadMapper::map);
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
    public MessageReadDto createMessage(MessageCreateDto message) {
        return Optional.of(message)
                .map(messageCreateMapper::map)
                .map(ms -> {
                    metaService.fillMeta(ms);
                    return messageRepository.saveAndFlush(ms);
                })
                .map(messageReadMapper::map).orElse(null);
    }

    @Override
    public List<MessageReadDto> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        return optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(() -> messageRepository.findAll())
                .stream().map(messageReadMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageEntity> findById(Long messageId) {
        return messageRepository.findById(messageId);
    }


}
