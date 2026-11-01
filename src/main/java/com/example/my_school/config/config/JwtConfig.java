package com.example.my_school.config.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtConfig {
    private String secretKey;
    private long jwtExpiration;
    private long refreshExpiration;
}
