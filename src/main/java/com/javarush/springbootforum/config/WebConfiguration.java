package com.javarush.springbootforum.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//@Configuration
public class WebConfiguration implements HandlerInterceptor { // todo попробовать с interceptors чего-нибудь сделать (ещё есть WebRequestHandlerInterceptorAdapter)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        request.getRequestURI();
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
