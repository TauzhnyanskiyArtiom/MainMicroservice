package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onpu.web.store.entity.UserSubscriptionEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileReadDto {
    String id;
    String name;
    String userpic;
    String locale;
    LocalDateTime lastVisit;
    List<UserSubscriptionReadDto> subscribers;
    List<UserSubscriptionReadDto> subscriptions;

}
