package com.reeo.book_network.email;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public abstract class EmailService<T> {
  @Async
  public abstract void sendMail(T mailRequest) throws MessagingException;
}
