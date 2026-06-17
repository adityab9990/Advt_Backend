
package com.project.advtRecords.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps the URL /files/** to the physical folder 'uploads' in your project root
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:uploads/");
    }
}