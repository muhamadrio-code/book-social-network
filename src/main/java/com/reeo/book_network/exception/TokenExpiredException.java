package com.reeo.book_network.exception;

public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException() {
    super("Token has expired. A new token has been send to the same email address");
  }
}
