package com.javarush.springbootforum.mapper;

import com.javarush.springbootforum.dto.UserCreateEditDto;
import com.javarush.springbootforum.dto.UserReadDto;
import com.javarush.springbootforum.entity.Role;
import com.javarush.springbootforum.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BaseDtoMapper.class})
public interface UserMapper {
    BaseDtoMapper baseDtoMapper = Mappers.getMapper(BaseDtoMapper.class);

    @Mapping(target = "lastActivity", dateFormat = " dd.MM.yyyy hh:mm:ss")
    @Mapping(target = "createdAt", dateFormat = "dd.MM.yyyy hh:mm:ss")
    UserReadDto toDto(User user);

    @Mapping(target = "password", expression = "java(setEncodingPasswordToUser(userCreateEditDto))")
    @Mapping(target = "role", expression = "java(setDefaultRoleToUser(userCreateEditDto))")
    @Mapping(target = "image", expression = "java(setDefaultAvatarImageToUser(userCreateEditDto))")
    User toEntity(UserCreateEditDto userCreateEditDto);

    @Mapping(target = "password", expression = "java(setEncodingPasswordToUser(userCreateEditDto))")
    User toEntity(@MappingTarget User user, UserCreateEditDto userCreateEditDto);


    default String setEncodingPasswordToUser(UserCreateEditDto userCreateEditDto) {
        return baseDtoMapper.passwordEncoder.encode(userCreateEditDto.getPassword());
    }

    default Role setDefaultRoleToUser(UserCreateEditDto userCreateEditDto) {
        return userCreateEditDto.getRole() == null ? Role.USER : Role.valueOf(userCreateEditDto.getRole());
    }

    default String setDefaultAvatarImageToUser(UserCreateEditDto userCreateEditDto) {
        return userCreateEditDto.getImage() == null ? "avatar_no_image.jpg" : userCreateEditDto.getImage();
    }

}
