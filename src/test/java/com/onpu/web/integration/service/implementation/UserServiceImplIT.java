package com.onpu.web.integration.service.implementation;

import com.onpu.web.integration.annotation.IT;
import com.onpu.web.service.implementation.UserServiceImpl;
import com.onpu.web.store.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserServiceImplIT {

    private static final String USER_ID = "1";

    private final UserServiceImpl userService;

    @Test
    void findById() {
        Optional<UserEntity> actualResult = userService.findById(USER_ID);

        assertTrue(actualResult.isPresent());
    }

    @Test
    void create(){
        UserEntity userTest = UserEntity.builder()
                .id("2")
                .name("Test")
                .email("Test")
                .locale("Test")
                .userpic("Test")
                .build();
        UserEntity user = userService.create(userTest);

        assertNotNull(user);
        
        assertEquals(userTest.getId(), user.getId());
        assertEquals(userTest.getName(), user.getName());
        assertEquals(userTest.getEmail(), user.getEmail());
    }

}