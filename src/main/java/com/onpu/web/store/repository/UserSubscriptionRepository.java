package com.onpu.web.store.repository;

import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import com.onpu.web.store.entity.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, UserSubscriptionId> {

    @Query("select chan from UserSubscriptionEntity u " +
            "left join u.subscriber sub " +
            "left join u.channel chan " +
            "where  sub.id = :userId and u.active = true")
    List<UserEntity> findBySubscriber(String userId);

    List<UserSubscriptionEntity> findByChannel(UserEntity channel);

    UserSubscriptionEntity findByChannelAndSubscriber(UserEntity channel, UserEntity subscriber);
}
