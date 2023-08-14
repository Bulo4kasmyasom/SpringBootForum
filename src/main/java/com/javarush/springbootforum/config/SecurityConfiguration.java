package com.javarush.springbootforum.config;


import com.javarush.springbootforum.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "",
                        "/",
                        "/home",
                        "/login",
                        "/users/**",
                        "/api/**",
                        "/js/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll()
                .requestMatchers("/topic-message/new")
                .hasAnyAuthority(Role.ADMIN.getAuthority(),
                        Role.MODERATOR.getAuthority(),
                        Role.USER.getAuthority())
                .anyRequest().permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/home", true)
                // todo что делать если авторизация не прошла. Написать.
                .and()
                .formLogin().failureForwardUrl("/home")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/home").invalidateHttpSession(true)
                .and()
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}