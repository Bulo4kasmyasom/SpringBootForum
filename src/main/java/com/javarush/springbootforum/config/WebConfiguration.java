package com.javarush.springbootforum.config;

import com.javarush.springbootforum.interceptor.PageTitleInterceptorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${page.title.beforeTitle}")
    private String beforeTitle;

    @Value("${page.title.afterTitle}")
    private String afterTitle;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageTitleInterceptorHandler(beforeTitle, afterTitle)).addPathPatterns("/**");
    }

}
