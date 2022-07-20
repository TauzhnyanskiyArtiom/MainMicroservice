package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
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
