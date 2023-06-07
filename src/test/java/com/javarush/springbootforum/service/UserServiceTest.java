package com.javarush.springbootforum.service;

import com.javarush.springbootforum.IntegrationBaseTest;
import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserServiceTest extends IntegrationBaseTest {

    private final UserService userService;

    private final UserCreateEditDto user = new UserCreateEditDto(
            "testUser",
            "testUser",
            "1.png",
            "email@email.com",
            "USER"
    );

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<UserReadDto> users = userService.findAll(pageable);
        assertThat(users).hasSize(3);
    }

    @Test
    void findById() {
        Optional<UserReadDto> user = userService.findById(1L);
        assertThat(user).isPresent();
    }

    @Test
    void findByUsername() {
        Optional<UserReadDto> user = userService.findByUsername("admin");
        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("admin");
    }

    @Test
    @WithAnonymousUser
    void createUserWithAnonymousUser() {
        UserReadDto userReadDto = userService.create(user);
        assertNotNull(userReadDto.getId());
        assertEquals(user.getUsername(), userReadDto.getUsername());
        assertEquals(user.getImage(), userReadDto.getImage());
        assertEquals(user.getEmail(), userReadDto.getEmail());
        assertEquals(user.getRole(), userReadDto.getRole());
    }

    @Test
    @WithAnonymousUser
    void createUserWhenUserExistNowByUsernameAndThrowException() { // this login already exists
        UserCreateEditDto user = new UserCreateEditDto(
                "admin",
                "admin",
                "1.png",
                "admin@admin.com",
                "ADMIN"
        );
        assertThatThrownBy(() -> userService.create(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with this login or email already exists");
    }

    @Test
    @WithAnonymousUser
    void createUserWhenUserExistNowByEmailAndThrowException() { // this email already exists
        UserCreateEditDto user = new UserCreateEditDto(
                "testUser",
                "testUser",
                "1.png",
                "admin@localhost.ru",
                "ADMIN"
        );
        assertThatThrownBy(() -> userService.create(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User with this login or email already exists");
    }


    @Test
    @WithMockUser(authorities = {"ADMIN", "MODERATOR", "USER"})
    void createUserWhenUserIsAuthenticated() {
        assertThatThrownBy(() -> userService.create(user))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithAnonymousUser
    void updateUserProfileWithAnonymousUserAndThrowException() {
        assertThatThrownBy(() -> userService.update(3L, user))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateUserProfileWithUserHaveAdminRole() {
        Optional<UserReadDto> userReadDto = userService.update(3L, user);
        assertThat(userReadDto).isPresent();
    }

    // todo проверить если пользователь сам себя пробует редактировать, а не админ

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteUserIfAdmin() {
        assertTrue(userService.delete(3L));
    }

    @Test
    @WithMockUser(authorities = {"USER", "MODERATOR"})
    void deleteUserIfNotAdmin() {
        assertThatThrownBy(() -> userService.delete(3L))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = userService.loadUserByUsername("admin");
        assertThat(userDetails.getUsername()).isEqualTo("admin");
    }
}