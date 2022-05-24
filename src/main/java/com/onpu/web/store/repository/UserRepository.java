package com.onpu.web.store.repository;

import com.onpu.web.store.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<UserEntity> findById(String id);

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    UserEntity getById(String id);

    @Query("select u from UserEntity u " +
            "left join fetch  u.subscriptions  " +
            "left join fetch u.subscribers  " +
            "where u.id = :id")
    UserEntity getProfile(String id);
}
