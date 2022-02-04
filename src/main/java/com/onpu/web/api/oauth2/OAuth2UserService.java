package com.onpu.web.api.oauth2;

import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends OidcUserService {

    UserService loggedUserService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        UserEntity user = saveUser(oidcUser);
        return new OAuth2User(oidcUser, user);
    }


    private UserEntity saveUser(OidcUser oidcUser){

        Map<String, Object> attributes = oidcUser.getAttributes();
        String id = (String) attributes.get("sub");

        UserEntity user = loggedUserService.findById(id).orElseGet(() -> {
            UserEntity newUser = UserEntity.builder()
                    .id(id)
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .locale((String) attributes.get("locale"))
                    .userpic((String) attributes.get("picture"))
                    .build();
            return newUser;
        });

        user.setLastVisit(LocalDateTime.now());

        UserEntity savedUser = loggedUserService.create(user);

        return savedUser;
    }


}
