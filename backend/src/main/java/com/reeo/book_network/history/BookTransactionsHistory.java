package com.reeo.book_network.history;

import com.reeo.book_network.book.Book;
import com.reeo.book_network.common.AuditableEntity;
import com.reeo.book_network.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_transaction_history")
@Entity
public class BookTransactionsHistory extends AuditableEntity {

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Setter
  private boolean returned;

  @Setter
  private boolean returnApproved;
}
