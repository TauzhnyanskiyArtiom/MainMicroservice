package com.onpu.web.service.implementation;

import com.onpu.web.api.dto.CommentCreateDto;
import com.onpu.web.api.dto.CommentReadDto;
import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.mapper.CommentCreateMapper;
import com.onpu.web.api.mapper.CommentReadMapper;
import com.onpu.web.api.util.WsSender;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.store.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BiConsumer;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CommentServiceImpl implements CommentService {

    CommentCreateMapper commentCreateMapper;
    CommentReadMapper commentReadMapper;

    CommentRepository commentRepository;

    BiConsumer<EventType, CommentReadDto> wsSender;

    public CommentServiceImpl(CommentCreateMapper commentCreateMapper, CommentReadMapper commentReadMapper, CommentRepository commentRepository, WsSender wsSender) {
        this.commentCreateMapper = commentCreateMapper;
        this.commentReadMapper = commentReadMapper;
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSenderNew(ObjectType.COMMENT);
    }

    @Transactional
    @Override
    public CommentReadDto create(CommentCreateDto comment) {
        return Optional.of(comment)
                .map(commentCreateMapper::map)
                .map(commentRepository::saveAndFlush)
                .map(commentReadMapper::map)
                .map(savedComment -> {
                        wsSender.accept(EventType.CREATE, savedComment);
                        return savedComment;
                }).orElse(null);
    }

    @Transactional
    @Override
    public boolean deleteMessage(Long commentId) {
        return  commentRepository.findById(commentId)
                .map(entity -> {
                    commentRepository.delete(entity);
                    commentRepository.flush();
                    CommentReadDto comment = commentReadMapper.map(entity);
                    wsSender.accept(EventType.REMOVE, comment);
                    return true;
                })
                .orElse(false);
    }
}
