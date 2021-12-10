package com.onpu.web.service.decorator;

import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedSubscriptionService implements SubscriptionService {

    @Qualifier("subscriptionServiceImpl")
    SubscriptionService subscriptionService;


    @Override
    public UserEntity changeSubscription(UserEntity channel, UserEntity subscriber) {
        log.info("Change subscription: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionService.changeSubscription(channel, subscriber);
    }

    @Override
    public List<UserSubscriptionEntity> getSubscribers(UserEntity channel) {
        log.info("Get subscribers: ");
        log.info("Channel id: " + channel.getId());
        log.info("Channel name: " + channel.getName());

        return subscriptionService.getSubscribers(channel);
    }

    @Override
    public UserSubscriptionEntity changeSubscriptionStatus(UserEntity channel, UserEntity subscriber) {
        log.info("Change subscription status: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionService.changeSubscriptionStatus(channel, subscriber);
    }
}
