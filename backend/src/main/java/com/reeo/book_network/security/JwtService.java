package com.reeo.book_network.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  public String generateToken(Map<String, Object> claims, UserDetails user) {
    return buildToken(claims, user, jwtExpiration);
  }

  private String buildToken(Map<String, Object> claims, UserDetails user, long jwtExpiration) {
    List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .claim("authorities", authorities)
        .signWith(getSignInKey())
        .compact();
  }

  public String extractUsername(@NonNull String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(
      @NonNull UserDetails userDetails,
      @NonNull String token
  ) {
    String username = extractUsername(token);
    return userDetails.getUsername().equals(username) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(@NotBlank String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(@NotBlank String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(
      @NotBlank String token,
      @NonNull Function<Claims, T> claimResolver
  ) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  @NonNull
  private Key getSignInKey() {
    final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims extractAllClaims(
      @NotBlank String token
  ) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }


}
