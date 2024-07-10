package com.reeo.book_network.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reeo.book_network.common.AuditableEntity;
import com.reeo.book_network.feedback.Feedback;
import com.reeo.book_network.history.BookTransactionsHistory;
import com.reeo.book_network.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity
@Setter
public class Book extends AuditableEntity {

  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "author_name")
  private String authorName;
  @Column(name = "isbn")
  private String isbn;
  @Column(name = "synopsis")
  private String synopsis;
  @Column(name = "book_cover")
  private String bookCover;
  @Column(name = "archived", nullable = false)
  private boolean archived;
  @Column(name = "shareable", nullable = false)
  private boolean shareable;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false, updatable = false)
  private User owner;

  @OneToMany(mappedBy = "book")
  private List<Feedback> feedbacks;

  @OneToMany(mappedBy = "book")
  private List<BookTransactionsHistory> histories;

  @JsonIgnore
  public double getRate() {
    if (feedbacks == null || feedbacks.isEmpty()) {
      return 0.0;
    }

    double rate = this.feedbacks
        .stream()
        .mapToDouble(Feedback::getNote)
        .average()
        .orElse(0.0);

    return Math.round(rate * 10.0) / 10.0;
  }

}
