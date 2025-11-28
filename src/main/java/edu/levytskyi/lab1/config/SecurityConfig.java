package edu.levytskyi.lab1.config;

/*
 @author Sandoplay
 @project lab1
 @class SecurityConfig
 @version 1.0.0
 @since 06.10.2025 - 21.21
*/

import edu.levytskyi.lab1.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public static Advisor preAuthorizeMethodInterceptor() {
    return AuthorizationManagerBeforeMethodInterceptor.preAuthorize();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/notes").permitAll()
            .requestMatchers("/api/v1/notes/helloUnknown").permitAll()

            .requestMatchers("/api/v1/notes/helloAdmin").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/notes/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/notes/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/v1/notes/**").hasAuthority("ADMIN")

            .requestMatchers("/api/v1/notes/helloUser").hasAnyAuthority("USER", "ADMIN")

            .anyRequest().authenticated()
        ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}