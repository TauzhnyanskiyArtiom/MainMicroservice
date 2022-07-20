package com.onpu.web.feignclient;

import com.onpu.web.api.dto.ChannelsDto;
import com.onpu.web.api.dto.MessageCreateDto;
import com.onpu.web.api.dto.MessageReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "message", url = "${message.url}")
public interface MessageClient {

    @PostMapping("/channels")
    List<MessageReadDto> findForChannels(ChannelsDto channelsDto);

    @GetMapping("{message_id}")
    Optional<MessageReadDto> getOne(@PathVariable("message_id") Long messageId);

    @PostMapping
    MessageReadDto addMessage(MessageCreateDto message);

    @PutMapping("{message_id}")
    MessageReadDto updateMessage(@PathVariable("message_id") Long messageId,  MessageCreateDto message);

    @DeleteMapping("{message_id}")
    boolean deleteMessage(@PathVariable("message_id") Long messageId);
}
