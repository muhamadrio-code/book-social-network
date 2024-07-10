package com.reeo.book_network.config;

import com.reeo.book_network.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
  @Override
  public Optional<Integer> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!auth.isAuthenticated() || auth.getPrincipal() == null || auth instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }
    User user = (User) auth.getPrincipal();
    return Optional.ofNullable(user.getId());
  }
}
