package com.onpu.web.store.repository;

import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.entity.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, UserSubscriptionId> {
    List<UserSubscriptionEntity> findBySubscriber(UserEntity user);

    List<UserSubscriptionEntity> findByChannel(UserEntity channel);

    UserSubscriptionEntity findByChannelAndSubscriber(UserEntity channel, UserEntity subscriber);
}
