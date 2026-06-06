package com.boardinghouse.boardinghouse_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins:}")
    private String[] allowedOrigins;

    @Value("${cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String[] allowedMethods;

    @Value("${cors.allowed-headers:Authorization,Content-Type}")
    private String[] allowedHeaders;

    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (allowedOrigins.length == 0 || allowedOrigins[0].isEmpty()) {
                    System.err.println("WARNING: No CORS origins configured. Cross-origin requests will be rejected.");
                    return;
                }
                
                registry.addMapping("/api/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods(allowedMethods)
                    .allowedHeaders(allowedHeaders)
                    .allowCredentials(allowCredentials)
                    .maxAge(3600);
                    
                System.out.println("CORS enabled for origins: " + String.join(", ", allowedOrigins));
            }
        };
    }
}