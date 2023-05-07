package com.javarush.springbootforum.service;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.mapper.DtoMapper;
import com.javarush.springbootforum.repository.UserRepository;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "users")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;

    @Cacheable(key = "#pageable")
    public Page<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(dtoMapper::userToUserReadDto);
    }

    @Cacheable(key = "#id")
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(dtoMapper::userToUserReadDto);
    }

    @Cacheable(key = "#username")
    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(dtoMapper::userToUserReadDto);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto user) {
        return Optional.of(user)
                .map(dtoMapper::userCreateEditDtoToUser)
                .map(userRepository::save)
                .map(dtoMapper::userToUserReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @CachePut(key = "#id") // todo метод findAll не обновляет список после обновления пользователя
    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto) {
        return userRepository.findById(id)
                .map(user -> dtoMapper.updateFromUserCreateEditDtoToUser(user, userCreateEditDto))
                .map(userRepository::saveAndFlush)
                .map(dtoMapper::userToUserReadDto);
    }

    @Transactional
    @CacheEvict(key = "#id")
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
