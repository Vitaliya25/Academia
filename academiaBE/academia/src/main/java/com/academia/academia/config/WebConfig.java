package com.academia.academia.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permite a todas las rutas
                .allowedOrigins("*") // permite todas las URLs externas (por ejemplo, tu app Android)
                .allowedMethods("*"); // permite GET, POST, PUT, DELETE, etc.
    }
}