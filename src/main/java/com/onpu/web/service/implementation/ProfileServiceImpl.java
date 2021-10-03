package com.onpu.web.service.implementation;

import com.onpu.web.service.interfaces.ProfileService;
import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.repository.UserRepository;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    UserRepository userRepository;

    UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public UserEntity changeSubscription(UserEntity channel, UserEntity subscriber) {
        List<UserSubscriptionEntity> subcriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                )
                .collect(Collectors.toList());

        if (subcriptions.isEmpty()) {
            UserSubscriptionEntity subscription = new UserSubscriptionEntity(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subcriptions);
        }

        return userRepository.save(channel);
    }

    @Override
    public UserEntity findById(String name) {
        return userRepository.findById(name).get();
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

    @Override
    public List<UserEntity> getUsers(Optional<String> optionalPrefixName) {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());
        List<UserEntity> users = optionalPrefixName
                .map(userRepository::findAllByNameContainingIgnoreCase)
                .orElseGet(() -> new ArrayList<>());

        return users;
    }
}
