package com.reeo.book_network.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

  @Bean
  public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
    return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
  }

  @Bean
  public ApplicationAuditAware applicationAuditAware() {
    return new ApplicationAuditAware();
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
    config.setAllowedHeaders(List.of(
        HttpHeaders.ORIGIN,
        HttpHeaders.CONTENT_TYPE,
        HttpHeaders.ACCEPT,
        HttpHeaders.AUTHORIZATION
    ));
    config.setAllowedMethods(List.of(
        "GET",
        "POST",
        "DELETE",
        "PUT",
        "PATCH"
    ));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
