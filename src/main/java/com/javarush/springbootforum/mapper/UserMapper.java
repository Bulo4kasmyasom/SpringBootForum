package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapperBase.class})
public interface UserMapper {
    @Mapping(target = "lastActivity", source = "lastActivity", dateFormat = " dd.MM.yyyy hh:mm:ss")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy hh:mm:ss")
    UserReadDto toDto(User user);

    @Mapping(target = "password", source = "password", qualifiedByName = "setEncodingPasswordToUser")
    @Mapping(target = "role", source = "role", qualifiedByName = "setDefaultRoleToUser")
    @Mapping(target = "image", source = "image", qualifiedByName = "setDefaultAvatarImageToUser")
    User toEntity(UserCreateEditDto userCreateEditDto);

    @Mapping(target = "password", source = "password", qualifiedByName = "setEncodingPasswordToUser")
    User toEntity(@MappingTarget User user, UserCreateEditDto userCreateEditDto);
}