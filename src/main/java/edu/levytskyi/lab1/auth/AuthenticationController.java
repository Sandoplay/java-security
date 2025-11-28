package edu.levytskyi.lab1.auth;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationController
 @version 1.0.0
 @since 28.11.2025 - 18.32
*/

import edu.levytskyi.lab1.DTO.AuthenticationRequest;
import edu.levytskyi.lab1.DTO.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(authenticationService.authenticate(request));
  }
}