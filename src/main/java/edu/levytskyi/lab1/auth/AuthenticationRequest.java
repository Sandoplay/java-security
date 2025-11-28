package edu.levytskyi.lab1.auth;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationRequest
 @version 1.0.0
 @since 28.11.2025 - 19.06
*/

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String username;
  private String password;
}