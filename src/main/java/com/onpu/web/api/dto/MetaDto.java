package com.onpu.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MetaDto {
    String title;
    String description;
    String cover;
}
