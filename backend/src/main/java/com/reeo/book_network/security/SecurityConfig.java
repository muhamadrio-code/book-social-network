package com.reeo.book_network.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      @NonNull HttpSecurity httpSecurity
  ) throws Exception {
    httpSecurity
        .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> {
              req.requestMatchers(
                  "/error",
                  "/auth/**",
                  "/v2/api-docs",
                  "/v3/api-docs",
                  "/v3/api-docs/**",
                  "/swagger-resources",
                  "/swagger-resources/**",
                  "/configuration/ui",
                  "/configuration/security",
                  "/swagger-ui/**",
                  "/webjars/**",
                  "/swagger-ui.html"
              ).permitAll();
              req.anyRequest().authenticated();
            }
        ).oauth2ResourceServer(auth -> auth.jwt(token -> token.jwtAuthenticationConverter(
            new KeycloakJwtAuthenticationConverter()
        )));

    return httpSecurity.build();
  }

}
