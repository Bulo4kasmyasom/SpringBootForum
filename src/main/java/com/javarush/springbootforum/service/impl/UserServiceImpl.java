package com.javarush.springbootforum.service.impl;

import com.javarush.springbootforum.controller.handler.exception.ResourceNotFoundException;
import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.mapper.UserMapper;
import com.javarush.springbootforum.repository.UserRepository;
import com.javarush.springbootforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheManager = "redisCacheManager")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserReadDto> findAll(Pageable pageable) {
        Sort.TypedSort<User> typedSort = Sort.sort(User.class);
        Sort sort = typedSort.by(User::getUsername).ascending();

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    @Cacheable(value = "UserService::findById", key = "#id", unless = "#result == null")
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional
    @PreAuthorize("!hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public UserReadDto create(UserCreateEditDto user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("User with this login or email already exists");
        }
        return Optional.of(user)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @CachePut(value = "UserService::findById", key = "#id")
    @Transactional
    @PreAuthorize("@userSecurityExpression.isUserOrAdminEdit(#id, 'ADMIN')")
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto) {
        String email = userCreateEditDto.getEmail();
        String username = userCreateEditDto.getUsername();

        Long countUsersByEmailOrUsername = userRepository.countUsersByEmailOrUsername(email, username);

        if (countUsersByEmailOrUsername > 1)
            throw new IllegalStateException("User with this login or email already exists");

        return userRepository.findById(id)
                .map(user -> userMapper.toEntity(user, userCreateEditDto))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::findById", key = "#id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> com.javarush.springbootforum.entity.User
                        .builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .role(user.getRole())
                        .image(user.getImage())
                        .authorities(Collections.singleton(user.getRole()))
                        .build()
                )
                .orElseThrow(() -> new ResourceNotFoundException("Failed to retrieve user" + username));
    }
}
