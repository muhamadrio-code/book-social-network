package com.reeo.book_network.exception;

public class OperationNotPermittedException extends RuntimeException {
  public OperationNotPermittedException() {
    super();
  }

  public OperationNotPermittedException(String message) {
    super(message);
  }
}
