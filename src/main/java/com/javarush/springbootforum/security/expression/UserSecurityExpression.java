package com.javarush.springbootforum.security.expression;

import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userSecurityExpression")
public class UserSecurityExpression { // used in service layer

    public boolean isUserOrAdminEdit(Long id, Role... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // todo костыль. Если не авторизован, но при этом isAuthenticated = true почему-то
        if (authentication.getName().equals("anonymousUser")) return false;

        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        return userId.equals(id) || hasAnyAuthority(authentication, roles);
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
