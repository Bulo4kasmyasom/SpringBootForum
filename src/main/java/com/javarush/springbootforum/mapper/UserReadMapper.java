package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getImage(),
                object.getRole().name(),
                object.getEmail(),
                object.getLastActivity().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                object.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
    }
}