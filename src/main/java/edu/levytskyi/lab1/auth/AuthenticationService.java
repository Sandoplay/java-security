package edu.levytskyi.lab1.auth;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationService
 @version 1.0.0
 @since 28.11.2025 - 18.32
*/

import edu.levytskyi.lab1.DTO.AuthenticationRequest;
import edu.levytskyi.lab1.DTO.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Проста перевірка (імітація перевірки в InMemoryUserDetailsManager)
    if ("admin".equals(request.getLogin()) && "admin".equals(request.getPassword())) {
      // Це реальний JWT токен (Header.Payload.Signature)
      // Payload: {"sub": "admin", "role": "ADMIN", "iat": 1516239022}
      String mockJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
          "eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTUxNjIzOTAyMn0." +
          "XbPfbC8Gq_8j1_7W4h6-5zJgXqQ_qXQ4k8Kk_K8qXqQ";
      return new AuthenticationResponse(mockJwtToken);
    }
    throw new RuntimeException("Invalid credentials");
  }
}