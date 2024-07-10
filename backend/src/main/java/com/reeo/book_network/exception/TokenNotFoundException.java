package com.reeo.book_network.exception;

public class TokenNotFoundException extends ResourceNotFoundException {
  public TokenNotFoundException() {
    super("Invalid token");
  }
}
