package com.reeo.book_network.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
  @Id
  @GeneratedValue
  private Integer id;

  @CreatedDate
  @Column(nullable = false, updatable = false, name = "created_date")
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  private Integer createdBy;

  @LastModifiedBy
  @Column(name = "last_modified_by", insertable = false)
  private Integer lastModifiedBy;
}
