package com.example.demo.config;

import com.example.demo.config.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/receitas/**")
                .excludePathPatterns(
                        "/login",
                        "/logout",
                        "/",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/static/**"
                );
    }
}
