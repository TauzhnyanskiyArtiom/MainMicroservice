package com.onpu.web.api.oauth2;

import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2User implements OidcUser, Serializable {

    OidcUser oauth2User;

    UserEntity user;

    public OAuth2User(OidcUser oauth2User, UserEntity user) {
        this.oauth2User = oauth2User;
        this.user = user;
    }



    @Override
    public Map<String, Object> getClaims() {
        return oauth2User.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oauth2User.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oauth2User.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getName();
    }

    public UserEntity getUser() {
        return user;
    }
}
