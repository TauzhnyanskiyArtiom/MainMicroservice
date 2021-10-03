package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    UserEntity changeSubscription(UserEntity channel, UserEntity subscriber);

    UserEntity findById(String name);

    List<UserSubscriptionEntity> getSubscribers(UserEntity channel);

    UserSubscriptionEntity changeSubscriptionStatus(UserEntity channel, UserEntity subscriber);

    List<UserEntity> getUsers(Optional<String> optionalPrefixName);

}
