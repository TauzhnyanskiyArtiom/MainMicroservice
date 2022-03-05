package com.onpu.web.store.repository;

import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Override
    @EntityGraph(attributePaths = { "comments", "author"})
    Optional<MessageEntity> findById(Long aLong);

    List<MessageEntity> findAllByTextContainingIgnoreCase(String prefixName);

    @EntityGraph(attributePaths = { "comments" })
    List<MessageEntity> findByAuthorIn(List<UserEntity> userEntity);
}
