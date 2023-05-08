package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    @Cacheable(key = "#pageable")
    Page<UserReadDto> findAll(Pageable pageable);

    @Cacheable(key = "#id")
    Optional<UserReadDto> findById(Long id);

    @Cacheable(key = "#username")
    Optional<UserReadDto> findByUsername(String username);

    @Transactional
    UserReadDto create(UserCreateEditDto user);

    @CachePut(key = "#id") // todo метод findAll не обновляет список после обновления пользователя
    @Transactional
    Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto);

    @Transactional
    @CacheEvict(key = "#id")
    boolean delete(Long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
