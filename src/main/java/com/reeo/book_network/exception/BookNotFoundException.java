package com.reeo.book_network.exception;

public class BookNotFoundException extends ResourceNotFoundException {
  public BookNotFoundException(String message) {
    super(message);
  }

}
