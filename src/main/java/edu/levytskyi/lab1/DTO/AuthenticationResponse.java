package edu.levytskyi.lab1.DTO;

/*
 @author Sandoplay
 @project lab1
 @class AuthenticationResponse
 @version 1.0.0
 @since 28.11.2025 - 18.31
*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
  private String token;
}