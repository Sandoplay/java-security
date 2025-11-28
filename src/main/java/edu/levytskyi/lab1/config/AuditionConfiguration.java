package edu.levytskyi.lab1.config;

/*
 @author Sandoplay
 @project lab1
 @class AuditionConfiguration
 @version 1.0.0
 @since 28.11.2025 - 20.10
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing // Вмикає автоматичний аудит
public class AuditionConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }
}