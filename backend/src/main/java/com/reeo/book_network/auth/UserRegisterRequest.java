package com.reeo.book_network.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {
  @NotEmpty
  @NotNull
  private final String firstname;

  @NotEmpty
  @NotNull
  private final String lastname;

  @NotEmpty
  @Email
  @NotNull
  private final String email;

  @NotEmpty
  @Size(min = 8)
  @NotNull
  private final String password;
}
