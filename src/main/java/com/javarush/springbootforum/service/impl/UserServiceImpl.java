package com.javarush.springbootforum.service.impl;

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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
//    @Cacheable(value = "UserService::findAll", key = "#pageable")
    public Page<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    @Cacheable(value = "UserService::findById", key = "#id")
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
    public UserReadDto create(UserCreateEditDto user) {
        return Optional.of(user)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @CachePut(value = "UserService::findById", key = "#id")
    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(user -> userMapper.toEntity(user, userCreateEditDto))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::findById", key = "#id")
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
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole()))
                )
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user" + username));
    }
}
