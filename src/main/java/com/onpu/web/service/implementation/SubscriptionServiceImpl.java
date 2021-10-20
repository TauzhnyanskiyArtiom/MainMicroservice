package com.onpu.web.service.implementation;

import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.UserRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    UserRepository userRepository;

    UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public UserEntity changeSubscription(UserEntity channel, UserEntity subscriber) {
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

        return userRepository.save(channel);
    }

    @Override
    public List<UserSubscriptionEntity> getSubscribers(UserEntity channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    @Override
    public UserSubscriptionEntity changeSubscriptionStatus(UserEntity channel, UserEntity subscriber) {
        System.out.println();
        UserSubscriptionEntity subscription = userSubscriptionRepository
                .findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepository.save(subscription);
    }
}
