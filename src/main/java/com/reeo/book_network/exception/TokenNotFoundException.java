package com.reeo.book_network.exception;

public class TokenNotFoundException extends RuntimeException {
  public TokenNotFoundException() {
    super("Invalid token");
  }
}
