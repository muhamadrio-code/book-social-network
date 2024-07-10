package com.reeo.book_network.email;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface MailService<T> {
  @Async
  void sendMail(T mailRequest) throws MessagingException;
}
