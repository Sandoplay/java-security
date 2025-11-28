package edu.levytskyi.lab1.config;

/*
 @author Sandoplay
 @project lab1
 @class SecurityConfig
 @version 1.0.0
 @since 06.10.2025 - 21.21
*/

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public static Advisor preAuthorizeMethodInterceptor() {
    return AuthorizationManagerBeforeMethodInterceptor.preAuthorize();
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll() // Додати цей рядок
            .requestMatchers(HttpMethod.GET, "/api/v1/notes").permitAll()
            // Публічний доступ
            .requestMatchers(HttpMethod.GET, "/api/v1/notes").permitAll()
            .requestMatchers("/api/v1/notes/helloUnknown").permitAll()

            // Доступ тільки для ADMIN
            .requestMatchers("/api/v1/notes/helloAdmin").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/notes/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/notes/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/v1/notes/**").hasRole("ADMIN")

            // Доступ для USER та ADMIN
            .requestMatchers("/api/v1/notes/helloUser").hasAnyRole("USER", "ADMIN")

            // Всі інші запити вимагають аутентифікації
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder().encode("user"))
        .roles("USER")
        .build();

    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build();

    UserDetails superadmin = User.builder()
        .username("superadmin")
        .password(passwordEncoder().encode("superadmin"))
        .roles("SUPERADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin, superadmin);
  }
}