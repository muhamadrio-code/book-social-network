package com.reeo.book_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "applicationAuditAware")
@EnableAsync
public class BookNetworkApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookNetworkApiApplication.class, args);
  }

//  @Bean
//  public CommandLineRunner runner(RoleRepository roleRepository) {
//    return args -> {
//      if (roleRepository.findByName("USER").isEmpty()) {
//        Role role = Role.builder()
//            .name("USER")
//            .createdDate(LocalDateTime.now())
//            .build();
//        roleRepository.save(role);
//      }
//    };
//  }

}
