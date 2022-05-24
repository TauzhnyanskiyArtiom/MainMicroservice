package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.UserSubscriptionReadDto;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import org.springframework.stereotype.Component;

@Component
public class UserSubscriptionReadMapper implements Mapper<UserSubscriptionEntity, UserSubscriptionReadDto>{
    @Override
    public UserSubscriptionReadDto map(UserSubscriptionEntity object) {
        return UserSubscriptionReadDto.builder()
                .channelId(object.getId().getChannelId())
                .subscriberId(object.getId().getSubscriberId())
                .build();
    }
}
