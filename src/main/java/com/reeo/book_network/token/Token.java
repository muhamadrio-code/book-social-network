package com.reeo.book_network.token;

import com.reeo.book_network.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Token {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(unique = true, name = "token", nullable = false)
  private String token;

  @CreatedDate
  @Column(name = "create_at", nullable = false)
  private LocalDateTime createAt;

  @Column(name = "expired_at", nullable = false)
  private LocalDateTime expiredAt;

  @Column(name = "verified_at")
  private LocalDateTime verifiedAt;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
}
