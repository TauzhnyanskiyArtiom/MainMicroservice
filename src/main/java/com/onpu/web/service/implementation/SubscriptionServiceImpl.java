package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.api.mapper.ProfileReadMapper;
import com.onpu.web.api.mapper.SubscriptionReadMapper;
import com.onpu.web.service.decorator.CashedUserService;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    CashedUserService userService;

    ProfileReadMapper profileReadMapper;

    SubscriptionReadMapper subscriptionReadMapper;

    UserSubscriptionRepository userSubscriptionRepository;

    @Transactional
    @Override
    public ProfileReadDto changeSubscription(UserEntity channel, UserEntity subscriber) {

        if (subscriber.equals(channel)) {
            return profileReadMapper.map(channel);
        }

        List<UserSubscriptionEntity> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                )
                .collect(Collectors.toList());

        if (subscriptions.isEmpty()) {
            UserSubscriptionEntity subscription = new UserSubscriptionEntity(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        UserEntity savedChannel = userService.saveSubscription(channel, subscriber);
        return profileReadMapper.map(savedChannel);
    }

    @Override
    public List<SubscriptionReadDto> getSubscribers(UserEntity channel) {
        return userSubscriptionRepository.findByChannel(channel)
                .stream()
                .map(subscriptionReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SubscriptionReadDto changeSubscriptionStatus(UserEntity channel, UserEntity subscriber) {
        UserSubscriptionEntity subscription = userSubscriptionRepository
                .findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        UserSubscriptionEntity savedSubscription = userSubscriptionRepository.saveAndFlush(subscription);
        return subscriptionReadMapper.map(savedSubscription);
    }
}
