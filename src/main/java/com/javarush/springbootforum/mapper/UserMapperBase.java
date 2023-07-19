package com.javarush.springbootforum.mapper;


import com.javarush.springbootforum.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Mapper
public class UserMapperBase {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Named("setEncodingPasswordToUser")
    public String setEncodingPasswordToUser(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("setDefaultRoleToUser")
    public Role setDefaultRoleToUser(String role) {
        return role == null ? Role.USER : Role.valueOf(role);
    }

    @Named("setDefaultAvatarImageToUser")
    public String setDefaultAvatarImageToUser(String image) {
        return image == null ? "avatar_no_image.jpg" : image;
    }

}