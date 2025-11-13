package edu.levytskyi.lab1.config;

/*
 @author Sandoplay
 @project lab1
 @class AuditingConfig
 @version 1.0.0
 @since 13.11.2025 - 16.28
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableMongoAuditing // Вмикає механізм аудиту
public class AuditingConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !authentication.isAuthenticated()) {
        return Optional.empty();
      }
      return Optional.of(authentication.getName()); // Повертає ім'я поточного юзера
    };
  }
}