package com.javarush.springbootforum.security.expression;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.TopicMessageReadDto;
import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.entity.User;
import com.javarush.springbootforum.service.TopicMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("topicMessageSecurityExpression")
@RequiredArgsConstructor
public class TopicMessageSecurityExpression { // used in service layer

    private final TopicMessageService topicMessageService;

    // only user message owner or moderator/admin can edit message
    public boolean canUserEditDeleteMessage(Long id, Role... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // todo костыль. Если не авторизован, но при этом isAuthenticated = true почему-то
        if (authentication.getName().equals("anonymousUser")) return false;

        TopicMessageReadDto topicMessage = topicMessageService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        Long topicMessageAuthorId = topicMessage.getAuthor().getId();

        User authUser = (User) authentication.getPrincipal();
        Long userId = authUser.getId();

        return userId.equals(topicMessageAuthorId) || hasAnyAuthority(authentication, roles);
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
