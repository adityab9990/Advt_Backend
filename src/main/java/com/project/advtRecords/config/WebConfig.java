package com.project.advtRecords.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry; // <--- THIS IS THE MISSING LINE
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // REMOVE addCorsMappings FROM HERE if it's already in SecurityConfig
    // This keeps the configuration centralized in SecurityConfig
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:uploads/");
    }
}
