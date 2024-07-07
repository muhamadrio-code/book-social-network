package com.reeo.book_network.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {
  private String token;
}
