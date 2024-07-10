package com.reeo.book_network.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@RequiredArgsConstructor
@ToString
@Getter
public class ActivateAccountMailRequest {
  @NotEmpty
  private final String to;

  @NotEmpty
  private final String subject;

  @NotEmpty
  private final String username;

  @NotEmpty
  private final String confirmationUrl;

  @NotEmpty
  private final String activationCode;
}
