package com.reeo.book_network.feedback;

import com.reeo.book_network.book.Book;
import com.reeo.book_network.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
@Entity
public class Feedback extends AuditableEntity {

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
  @Column(name = "note")
  private Double note;
  @Column(name = "comment")
  private String comment;
}
