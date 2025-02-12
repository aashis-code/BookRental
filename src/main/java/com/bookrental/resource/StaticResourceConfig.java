package com.bookrental.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/images/")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/images/profile/")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/images/serviceGroup/");
    }

}
