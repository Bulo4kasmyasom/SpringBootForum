package com.javarush.springbootforum.security.expression;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userSecurityExpression")
@RequiredArgsConstructor
public class UserSecurityExpression { // used in service layer

    private final UserService userService;

    public boolean isUserOrAdminEdit(Long id, Role... roles) { // the user can edit his profile or the admin can do it
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // todo костыль. Если не авторизован, но при этом isAuthenticated = true почему-то
        if (authentication.getName().contains("anonymous")) return false;

        UserReadDto userReadDto = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String authUserName = authentication.getName();

        return userReadDto.getUsername().equals(authUserName) || hasAnyAuthority(authentication, roles);
    }

    private boolean hasAnyAuthority(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());

            if (authentication.getAuthorities().contains(authority))
                return true;
        }
        return false;
    }
}
