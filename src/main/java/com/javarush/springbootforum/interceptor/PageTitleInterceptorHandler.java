package com.javarush.springbootforum.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
public class PageTitleInterceptorHandler implements HandlerInterceptor {

    private String beforeTitle;
    private String afterTitle;
    private String defaultTitle;

    @SuppressWarnings("NullableProblems") // response and handler require @NonNullApi
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            if (modelAndView.getModel().get("pageTitle") != null) {
                String pageTitle = (String) modelAndView.getModel().get("pageTitle");
                String fullPageTitle = beforeTitle + pageTitle + afterTitle;
                modelAndView.getModel().put("pageTitle", fullPageTitle);
            } else {
                modelAndView.getModel().put("pageTitle", defaultTitle);
            }
        }
    }

}