package com.reeo.book_network.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationRequest {
  @Email
  @NotNull
  private String email;

  @NotNull
  @Size(min = 8)
  private String password;
}
