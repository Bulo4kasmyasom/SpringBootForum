package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Page<UserReadDto> findAll(Pageable pageable);

    Optional<UserReadDto> findById(Long id);

    Optional<UserReadDto> findByUsername(String username);

    @Transactional
    UserReadDto create(UserCreateEditDto user);

    @Transactional
    Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto);

    @Transactional
    boolean delete(Long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
