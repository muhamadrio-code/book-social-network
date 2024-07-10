package com.reeo.book_network.book;

import com.reeo.book_network.user.User;
import com.reeo.book_network.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;


  @Autowired
  private EntityManager entityManager;

  private Book book;
  private User user;


  @BeforeEach
  void setUp() {
    User save = userRepository.save(
        User.builder()
            .firstName("")
            .lastName("")
            .password("12345678")
            .email("user@mail.com")
            .accountLocked(false)
            .enabled(false)
            .build()
    );
    user = entityManager.find(User.class, save.getId());

    book = Book.builder()
        .id(1)
        .archived(false)
        .shareable(true)
        .owner(user)
        .createdBy(user.getId())
        .build();
  }

  @Test
  @DisplayName("Find all displayable books, should return books that does not belong to userId")
  void testFindAllDisplayableBooks() {
    Book savedBook = bookRepository.save(book);

    Page<Book> allDisplayableBooks = bookRepository
        .findAllDisplayableBooks(Pageable.unpaged(), 0);
    List<Book> list = allDisplayableBooks.stream().toList();

    assertThat(list).contains(savedBook);
  }

  @Test
  @DisplayName("Find all displayable books, should return empty list")
  void testFindAllDisplayableBooksEmpty() {
    Book savedBook = bookRepository.save(book);

    Page<Book> allDisplayableBooks = bookRepository
        .findAllDisplayableBooks(Pageable.unpaged(), user.getId());
    List<Book> list = allDisplayableBooks.stream().toList();

    assertThat(list).doesNotContain(savedBook);
  }
}