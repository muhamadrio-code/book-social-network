package com.reeo.book_network.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reeo.book_network.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@Table(name = "_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User implements UserDetails, Principal {

  @Id
  @GeneratedValue
  private long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "account_locked", nullable = false)
  private boolean accountLocked;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Role> roles;

  @Column(name = "created_date", updatable = false, nullable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date", insertable = false)
  private LocalDateTime lastModifiedDate;

  public String getFullname() {
    return firstName + " " + lastName;
  }

  @Override
  public String getName() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles
        .stream()
        .map(r ->
            new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()
        );
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
