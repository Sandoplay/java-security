package edu.levytskyi.lab1.config;

/*
 @author Sandoplay
 @project lab1
 @class MongoConfig
 @version 1.0.0
 @since 13.11.2025 - 17.31
*/

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

  // Явно створюємо бін mongoTemplate, якого не вистачає вашому застосунку
  @Bean
  public MongoTemplate mongoTemplate(MongoClient mongoClient) {
    // "security_lab1" - це назва вашої бази даних.
    // Вона буде використана замість тої, що в пропертіс.
    return new MongoTemplate(mongoClient, "security_lab1");
  }
}