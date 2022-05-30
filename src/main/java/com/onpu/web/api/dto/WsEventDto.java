package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class WsEventDto {
    ObjectType objectType;
    EventType eventType;
    @JsonRawValue
    String body;
}
