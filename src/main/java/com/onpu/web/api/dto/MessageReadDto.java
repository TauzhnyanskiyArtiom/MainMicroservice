package com.onpu.web.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageReadDto {
    private Long id;
    private String text;
    private UserReadDto author;
    private List<CommentReadDto> comments;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
}
