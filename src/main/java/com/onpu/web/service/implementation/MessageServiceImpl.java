package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.exception.NotFoundException;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
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

        return messageRepository.findByAuthorIn(channels);
    }

    @Async
    @Transactional
    @Override
    public CompletableFuture<MessageEntity> updateMessage(Long messageId, MessageEntity message) {

        CompletableFuture<MessageEntity> completableFutureMessage = CompletableFuture.supplyAsync(() -> {
            MessageEntity messageFromDb = getMessageEntity(messageId);
            BeanUtils.copyProperties(message, messageFromDb, "id", "comments","author", "createdAt", "modifiedAt");
            return messageFromDb;

        }).thenApplyAsync((resultMessage) -> {
            metaService.fillMeta(resultMessage);
            return resultMessage;
        }).thenApplyAsync((resultMessage) -> messageRepository.saveAndFlush(resultMessage)
        ).thenApplyAsync((resultMessage) -> {
            wsSender.accept(EventType.UPDATE, resultMessage);
            return resultMessage;
        });


        return completableFutureMessage;
    }


    @Async
    @Override
    public CompletableFuture<Void> deleteMessage(Long messageId) {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            MessageEntity message = getMessageEntity(messageId);
            messageRepository.delete(message);
            return message;
        }).thenAcceptAsync((resultMessage) -> wsSender.accept(EventType.REMOVE, resultMessage));

        return completableFuture;

    }

    @Async
    @Override
    public CompletableFuture<MessageEntity> createMessage(MessageEntity message, UserEntity user)  {

        CompletableFuture<MessageEntity> completableFutureMessage = CompletableFuture.supplyAsync(() -> {
            message.setAuthor(user);
            return message;
        }).thenApplyAsync((resultMessage) -> {
            metaService.fillMeta(resultMessage);
            return resultMessage;
        }).thenApplyAsync(resultMessage -> messageRepository.save(resultMessage));

        return completableFutureMessage;
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        return optionalPrefixName
                .map(messageRepository::findAllByTextContainingIgnoreCase)
                .orElseGet(() -> messageRepository.findAll());
    }

    private MessageEntity getMessageEntity(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("Message don`t found"));
    }

}
