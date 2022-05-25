package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.service.interfaces.SubscriptionService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedSubscriptionService implements SubscriptionService {

    SubscriptionService subscriptionServiceImpl;


    @Override
    public ProfileReadDto changeSubscription(UserEntity channel, UserEntity subscriber) {
        log.info("Change subscription: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionServiceImpl.changeSubscription(channel, subscriber);
    }

    @Override
    public List<SubscriptionReadDto> getSubscribers(UserEntity channel) {
        log.info("Get subscribers: ");
        log.info("Channel id: " + channel.getId());
        log.info("Channel name: " + channel.getName());

        return subscriptionServiceImpl.getSubscribers(channel);
    }

    @Override
    public SubscriptionReadDto changeSubscriptionStatus(UserEntity channel, UserEntity subscriber) {
        log.info("Change subscription status: ");
        log.info("Channel id: " + channel.getId());
        log.info("Subscriber text: " + subscriber.getId());

        return subscriptionServiceImpl.changeSubscriptionStatus(channel, subscriber);
    }
}
