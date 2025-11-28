package edu.levytskyi.lab1.auth;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationService
 @version 1.0.0
 @since 28.11.2025 - 18.32
*/

import edu.levytskyi.lab1.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // 1. Перевірка логіну/паролю (якщо невірно - викине помилку)
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    // 2. Завантаження даних користувача (з BeansConfiguration)
    UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

    // 3. Генерація токена
    String jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}