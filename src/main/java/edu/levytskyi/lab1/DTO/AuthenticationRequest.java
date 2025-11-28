package edu.levytskyi.lab1.DTO;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationRequest
 @version 1.0.0
 @since 28.11.2025 - 18.30
*/

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String login;
  private String password;
}