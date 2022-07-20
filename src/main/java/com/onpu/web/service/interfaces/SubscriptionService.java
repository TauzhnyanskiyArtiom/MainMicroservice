package com.onpu.web.service.interfaces;

import java.util.List;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.store.entity.UserEntity;

public interface SubscriptionService {

    ProfileReadDto changeSubscription(UserEntity channel, UserEntity subscriber);

    List<SubscriptionReadDto> getSubscribers(UserEntity channel);

    SubscriptionReadDto changeSubscriptionStatus(UserEntity channel, UserEntity subscriber);
}
