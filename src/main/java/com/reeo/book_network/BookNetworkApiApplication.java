package com.reeo.book_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaAuditing
public class BookNetworkApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookNetworkApiApplication.class, args);
  }

}
