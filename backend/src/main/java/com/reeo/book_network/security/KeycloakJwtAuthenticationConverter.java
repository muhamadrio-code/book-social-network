package com.reeo.book_network.security;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt source) {
    return new JwtAuthenticationToken(
        source,
        Stream.concat(
            new JwtGrantedAuthoritiesConverter().convert(source).stream(),
            extractResourceAccesStream(source)
        ).collect(Collectors.toSet())
    );
  }

  @SuppressWarnings({"unchecked"})
  private Stream<? extends GrantedAuthority> extractResourceAccesStream(Jwt jwt) {
    HashMap<Object, Object> resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
    Map<String, List<String>> o = (Map<String, List<String>>) resourceAccess.get("account");
    List<String> roles = o.get("roles");
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace('-', '_')));
  }

}
