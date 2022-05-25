package com.onpu.web.store.repository;

import com.onpu.web.store.entity.MessageEntity;
import com.onpu.web.store.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends
        JpaRepository<MessageEntity, Long>,
        RevisionRepository<MessageEntity, Long, Long> {

    @Override
//    @EntityGraph("withCommentsAndAuthor")
    @Query("select m from MessageEntity m " +
            "left join fetch m.comments c " +
            "join fetch m.author u " +
            "left join u.subscribers " +
            "left join u.subscriptions " +
            "where m.id = :messageId")
    Optional<MessageEntity> findById(Long messageId);

    List<MessageEntity> findAllByTextContainingIgnoreCase(String prefixName);

    @EntityGraph(attributePaths = { "comments" })
    List<MessageEntity> findByAuthorIn(List<UserEntity> userEntity, Sort sort);

}
