package com.onpu.web.service.interfaces;

import java.util.List;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;

public interface SubscriptionService {

    UserEntity changeSubscription(UserEntity channel, UserEntity subscriber);

    List<UserSubscriptionEntity> getSubscribers(UserEntity channel);

    UserSubscriptionEntity changeSubscriptionStatus(UserEntity channel, UserEntity subscriber);
}
