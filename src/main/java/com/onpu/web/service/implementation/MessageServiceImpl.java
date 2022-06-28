package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.ChannelsDto;
import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.feignclient.MessageClient;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    MessageClient messageClient;

    UserSubscriptionRepository userSubscriptionRepository;


    @Override
    public List<MessageReadDto> findForUser(UserEntity userEntity) {
        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity.getName());
        channels.add(userEntity);

        List<String> channelsId = channels.stream().map(UserEntity::getId).collect(Collectors.toList());
        return messageClient.findForChannels(new ChannelsDto(channelsId));
    }


    @Override
    public MessageReadDto updateMessage(Long messageId, MessageCreateDto message) {
        return messageClient.updateMessage(messageId, message);
    }


    @Override
    public boolean deleteMessage(Long messageId) {
        return messageClient.deleteMessage(messageId);
    }

    @Override
    public MessageReadDto createMessage(MessageCreateDto message) {
        return messageClient.addMessage(message);

    }


    @Override
    public Optional<MessageReadDto> findById(Long messageId) {
        return messageClient.getOne(messageId);
    }


}
