package com.reeo.book_network.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionsHistoryRepository extends JpaRepository<BookTransactionsHistory, Integer> {
  @Query("""
      SELECT history
      FROM BookTransactionsHistory history
      WHERE history.createdBy = :userId
      """)
  Page<BookTransactionsHistory> findAllHistoriesByUserId(String userId,
                                                         Pageable pageable);

  @Query("""
      SELECT history
      FROM BookTransactionsHistory history
      WHERE history.book.createdBy = :userId
      AND history.returned = true
      """)
  Page<BookTransactionsHistory> findAllReturnedBookHistoriesByUserId(String userId,
                                                                     Pageable pageable);

}