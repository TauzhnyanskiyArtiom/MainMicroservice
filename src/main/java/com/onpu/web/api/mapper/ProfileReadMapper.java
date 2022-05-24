package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.ProfileReadDto;
import com.onpu.web.api.dto.UserSubscriptionReadDto;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileReadMapper implements Mapper<UserEntity, ProfileReadDto>{

    UserSubscriptionReadMapper userSubscriptionReadMapper;

    @Override
    public ProfileReadDto map(UserEntity object) {
        return ProfileReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .userpic(object.getUserpic())
                .lastVisit(object.getLastVisit())
                .locale(object.getLocale())
                .subscribers(getUserSubscriptionReadDto(object.getSubscribers()))
                .subscriptions(getUserSubscriptionReadDto(object.getSubscriptions()))
                .build();
    }

    private List<UserSubscriptionReadDto> getUserSubscriptionReadDto(Set<UserSubscriptionEntity> set){
        return set.stream().map(userSubscriptionReadMapper::map).collect(Collectors.toList());
    }
}
