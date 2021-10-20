package com.onpu.web.service.interfaces;

import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.entity.UserSubscriptionEntity;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    UserEntity findById(String name);

    List<UserEntity> getUsers(Optional<String> optionalPrefixName);

}
