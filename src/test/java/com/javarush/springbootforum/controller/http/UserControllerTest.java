package com.javarush.springbootforum.controller.http;

import com.javarush.springbootforum.IntegrationBaseTest;
import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;

import java.util.Objects;

import static com.javarush.springbootforum.dto.UserCreateEditDto.Fields.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class UserControllerTest extends IntegrationBaseTest {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("pageTitle", "users"));
//                .andExpect(model().attribute("users", PageResponseDto.class))
//                .andExpect(model().attribute("pageTitle", notNullValue()));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(view().name("user"))
                .andExpect(model().attributeExists("pageTitle", "user", "roles", "defaultAvatars"));
    }

    @Test
    public void findByIdAndNotFountThenExpectRedirect() throws Exception {
        mockMvc.perform(get("/users/{id}", 0))
//                .andExpect(redirectedUrl("/users"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users/registration")
                        .param(username, "test")
                        .param(password, "test")
                        .param(image, "1.png")
                        .param(email, "test@gmail.com")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/home")
                );
    }

    @Test
    void createAndCheckUserValidation() throws Exception {
        mockMvc.perform(post("/users/registration")
                        .param(username, "t")
                        .param(password, "t")
                        .param(image, "1.png")
                        .param(email, "t")
                        .param(role, "ADMIN")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(flash().attributeExists("errors"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateUserByAdmin() throws Exception {
        mockMvc.perform(post("/users/{id}/update", 3)
                        .param(username, "test")
                        .param(password, "test")
                        .param(image, "1.png")
                        .param(email, "test@test.ru")
                        .param(role, "USER")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    @WithMockUser(username = "moderator", authorities = "MODERATOR")
    void updateUserHimselfByUsername() throws Exception {
        mockMvc.perform(post("/users/{id}/update", 2)
                        .param(username, "test")
                        .param(password, "test")
                        .param(image, "1.png")
                        .param(email, "test@test.ru")
                        .param(role, "USER")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    @WithMockUser(username = "user", authorities = "ADMIN")
    void updateAndCheckUserValidation() throws Exception {
        mockMvc.perform(post("/users/{id}/update", 3)
                        .param(username, "t")
                        .param(password, "t")
                        .param(image, "1.png")
                        .param(email, "t")
                        .param(role, "ADMIN")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                .andExpect(flash().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "user", authorities = "ADMIN")
    void updateAndCheckByExistUser() throws Exception {
        mockMvc.perform(post("/users/{id}/update", 0)
                        .param(username, "test")
                        .param(password, "test")
                        .param(image, "1.png")
                        .param(email, "test@test.ru")
                        .param(role, "USER")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("User not found",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(flash().attributeExists("errors"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void delete() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", 3))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void deleteAndThrowExceptionWhenUserNotFound() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", 0))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("User not found, not deleted",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}