package com.onpu.web.service.decorator;


import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CashedMessageService implements MessageService {

    @Qualifier("messageServiceImpl")
    MessageService messageService;

    SubscriptionService subscriptionService;

    @NonFinal
    HashMap<String, List<MessageEntity>> cashedMessage;

    @PostConstruct
    public void init(){
        cashedMessage = new HashMap<>();
    }

    @Override
    public List<MessageEntity> findForUser(UserEntity userEntity) {

        List<MessageEntity> messages = cashedMessage.get(userEntity.getId());

        if (Objects.isNull(messages)) {
            messages  = messageService.findForUser(userEntity);
            cashedMessage.put(userEntity.getId(), messages);

        }

        return messages;
    }

    @Override
    public MessageEntity updateMessage(MessageEntity messageFromDB, MessageEntity message) {
        MessageEntity updatedMessage = messageService.updateMessage(messageFromDB, message);

        List<UserEntity> subscribers = getAllSubscribers(updatedMessage.getAuthor());

        for (UserEntity subscriber: subscribers) {
            List<MessageEntity> messages = cashedMessage.get(subscriber.getId());
            if (!Objects.isNull(messages)) {

                int indexMessage = messages.indexOf(messageFromDB);
                messages.set(indexMessage, updatedMessage);
            }
        }

        return updatedMessage;
    }

    @Override
    public void deleteMessage(MessageEntity message) {
        List<UserEntity> subscribers = getAllSubscribers(message.getAuthor());

        for (UserEntity subscriber: subscribers) {
            List<MessageEntity> messages = cashedMessage.get(subscriber.getId());
            if (!Objects.isNull(messages))
                messages.remove(message);
        }

        messageService.deleteMessage(message);
    }

    @Override
    public MessageEntity createMessage(MessageEntity message, UserEntity user) {
        MessageEntity savedMessage = messageService.createMessage(message, user);

        List<UserEntity> subscribers = getAllSubscribers(user);
        subscribers.stream().forEach(u -> cashedMessage.remove(u.getId()));

        return savedMessage;
    }

    @Override
    public List<MessageEntity> getListMessages(Optional<String> optionalPrefixName) {
        return messageService.getListMessages(optionalPrefixName);
    }

    private List<UserEntity> getAllSubscribers(UserEntity user){
        List<UserEntity> subscribers = subscriptionService
                .getSubscribers(user)
                .stream()
                .map(subs -> subs.getSubscriber())
                .collect(Collectors.toList());

        subscribers.add(user);
        return subscribers;
    }
}
