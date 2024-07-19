package com.reeo.book_network.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!auth.isAuthenticated() || auth.getPrincipal() == null || auth instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }
    String user = auth.getName();
    return Optional.ofNullable(user);
  }
}
