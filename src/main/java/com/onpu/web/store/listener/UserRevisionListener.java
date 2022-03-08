package com.onpu.web.store.listener;

import com.onpu.web.api.oauth2.OAuth2User;
import com.onpu.web.store.entity.RevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object o) {
        RevisionEntity revisionEntity = (RevisionEntity) o;
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        revisionEntity.setUserId(user.getName());
        revisionEntity.setUsername(user.getUserName());
    }
}
