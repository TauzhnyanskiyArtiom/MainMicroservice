package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.SubscriptionReadDto;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionReadMapper implements Mapper<UserSubscriptionEntity, SubscriptionReadDto>{

    private final UserReadMapper userReadMapper;

    @Override
    public SubscriptionReadDto map(UserSubscriptionEntity object) {
        return SubscriptionReadDto.builder()
                .subscriber(userReadMapper.map(object.getSubscriber()))
                .active(object.isActive())
                .build();
    }
}
