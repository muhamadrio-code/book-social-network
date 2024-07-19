package com.reeo.book_network.book;

import com.reeo.book_network.common.AuditableEntity;
import com.reeo.book_network.common.PageResponse;
import com.reeo.book_network.exception.BookNotFoundException;
import com.reeo.book_network.exception.OperationNotPermittedException;
import com.reeo.book_network.file.FileStorageService;
import com.reeo.book_network.history.BookTransactionsHistory;
import com.reeo.book_network.history.BookTransactionsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

import static com.reeo.book_network.book.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final BookTransactionsHistoryRepository bookTransactionsHistoryRepository;
  private final FileStorageService fileStorageService;

  public Integer save(SaveBookRequest request, Authentication authentication) {
    final Book book = bookMapper.toBook(request);
    book.setCreatedBy(authentication.getName());
    return bookMapper.toResponse(
        bookRepository.save(book)
    ).getId();
  }

  public BookResponse findById(Integer bookId) {
    return bookMapper.toResponse(findBookById(bookId));
  }

  public PageResponse<BookResponse> findAllBooks(
      int page, int size, Authentication authentication
  ) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
    final Page<BookResponse> bookResponses = bookRepository
        .findAllDisplayableBooks(pageRequest, authentication.getName())
        .map(bookMapper::toResponse);

    return buildPagedApiResponses(bookResponses);
  }

  public PageResponse<BookResponse> findAllBooksByOwner(
      int page, int size, Authentication authentication
  ) {
    final PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<BookResponse> bookResponses = bookRepository
        .findAll(withOwnerId(authentication.getName()), pageRequest)
        .map(bookMapper::toResponse);

    return buildPagedApiResponses(bookResponses);
  }

  public Integer updateShareableStatus(Integer bookId, Authentication authentication) {
    Book book = findBookById(bookId);
    if (!book.getCreatedBy().equals(authentication.getName())) {
      throw new OperationNotPermittedException(
          "You cannot update others book shareable status"
      );
    }
    book.setShareable(!book.isShareable());
    bookRepository.save(book);
    return book.getId();
  }

  public Integer updateArchivedStatus(Integer bookId, Authentication authentication) {
    Book book = findBookById(bookId);
    if (!book.getCreatedBy().equals(authentication.getName())) {
      throw new OperationNotPermittedException(
          "You cannot update others book archived status"
      );
    }
    book.setArchived(!book.isArchived());
    bookRepository.save(book);
    return book.getId();
  }

  @Transactional
  public Integer borrowBook(Integer bookId, Authentication authentication) {
    Book book = findBookById(bookId);
    String userId = authentication.getName();
    if (!book.isShareable() || book.isArchived()) {
      throw new OperationNotPermittedException(
          "The request book cannot be borrowed since it is archived or not shareable"
      );
    }

    boolean bookIsMine = book.getCreatedBy().equals(userId);
    if (bookIsMine) {
      throw new OperationNotPermittedException(
          "You cannot borrow your own book"
      );
    }

    boolean transactionHistoryIsEmpty = book.getHistories().isEmpty();

    if (!transactionHistoryIsEmpty) {
      BookTransactionsHistory lastTransaction = getBookLastTransactionHistory(book);
      boolean isReturnedOrApproved = lastTransaction.isReturned() || lastTransaction.isReturnApproved();
      boolean isBorrowedByMe = lastTransaction.getCreatedBy().equals(userId);
      if (!isReturnedOrApproved) {
        if (isBorrowedByMe) {
          throw new OperationNotPermittedException(
              "You already borrowed this book and it is still not returned or the return is not approved by the owner"
          );
        } else {
          throw new OperationNotPermittedException("The requested book is already borrowed");
        }
      }
    }

    return createNewTransaction(book, userId);
  }

  public Integer returnBorrowedBook(Integer bookId, Authentication authentication) {
    Book book = findBookById(bookId);
    String userId = authentication.getName();
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("The requested book is archived or not shareable");
    }
    if (book.getCreatedBy().equals(userId)) {
      throw new OperationNotPermittedException(
          "Cannot borrow or return your own book"
      );
    }
    BookTransactionsHistory lastTransaction = getBookLastTransactionHistory(book);
    if (lastTransaction.isReturned()) {
      throw new OperationNotPermittedException(
          "You already returned the Book or Book returned is not approved yet"
      );
    }
    if (!lastTransaction.getCreatedBy().equals(userId)) {
      throw new OperationNotPermittedException(
          "You cannot returned a book that borrowed by other user"
      );
    }
    lastTransaction.setReturned(true);
    bookTransactionsHistoryRepository.save(lastTransaction);
    return lastTransaction.getId();
  }

  public Integer approveReturnedBook(Integer bookId, Authentication authentication) {
    Book book = findBookById(bookId);
    String userId = authentication.getName();
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("The requested book is archived or not shareable");
    }
    if (!book.getCreatedBy().equals(userId)) {
      throw new OperationNotPermittedException("You did not own this book");
    }

    BookTransactionsHistory lastTransaction = getBookLastTransactionHistory(book);
    if (lastTransaction.isReturnApproved()) {
      throw new OperationNotPermittedException(
          "You already approved this Book"
      );
    }
    if (!lastTransaction.isReturned()) {
      throw new OperationNotPermittedException(
          "The Book is not returned yet"
      );
    }
    lastTransaction.setReturnApproved(true);
    bookTransactionsHistoryRepository.save(lastTransaction);
    return lastTransaction.getId();
  }

  public void uploadBookCoverPicture(Integer bookId, Authentication authentication, MultipartFile picture) {

    Book book = findBookById(bookId);
    String userId = authentication.getName();
    String pathString = fileStorageService.saveFile(picture, userId);
    book.setBookCover(pathString);
    bookRepository.save(book);
  }

  public PageResponse<BorrowedBookResponse> findAllBorrowedBook(int page, int size, Authentication authentication) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
    String userId = authentication.getName();
    Page<BorrowedBookResponse> responses = bookTransactionsHistoryRepository
        .findAllHistoriesByUserId(userId, pageRequest)
        .map(this::getBorrowedBookResponseMapper);
    return buildPagedApiResponses(responses);
  }

  public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication authentication) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
    String userId = authentication.getName();
    Page<BorrowedBookResponse> responses = bookTransactionsHistoryRepository
        .findAllReturnedBookHistoriesByUserId(userId, pageRequest)
        .map(this::getBorrowedBookResponseMapper);
    return buildPagedApiResponses(responses);
  }

  private BorrowedBookResponse getBorrowedBookResponseMapper(BookTransactionsHistory history) {
    return BorrowedBookResponse.builder()
        .returned(history.isReturned())
        .title(history.getBook().getTitle())
        .rate(history.getBook().getRate())
        .id(history.getBook().getId())
        .isbn(history.getBook().getIsbn())
        .authorName(history.getBook().getAuthorName())
        .returnApproved(history.isReturnApproved())
        .coverPicture(history.getBook().getBookCover())
        .build();
  }

  private Integer createNewTransaction(Book book, String userId) {
    BookTransactionsHistory transactionsHistory = BookTransactionsHistory.builder()
        .book(book)
        .createdBy(userId)
        .returned(false)
        .returnApproved(false)
        .build();

    BookTransactionsHistory saved = bookTransactionsHistoryRepository.save(transactionsHistory);
    return saved.getId();
  }

  private BookTransactionsHistory getBookLastTransactionHistory(Book book) {
    if (book.getHistories().isEmpty()) throw new IllegalStateException("The Book transaction history is empty");
    List<BookTransactionsHistory> bookTransactionsHistories = getBookTransactionHistories(book);
    int lastIndex = bookTransactionsHistories.size() - 1;
    return bookTransactionsHistories.get(lastIndex);
  }

  private List<BookTransactionsHistory> getBookTransactionHistories(Book book) {
    return book.getHistories().stream()
        .sorted(Comparator.comparing(AuditableEntity::getCreatedDate))
        .toList();
  }

  private Book findBookById(Integer bookId) throws BookNotFoundException {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new BookNotFoundException("No book found with ID:: " + bookId));
  }

  private <T> PageResponse<T> buildPagedApiResponses(Page<T> bookResponses) {
    return PageResponse.<T>builder()
        .content(bookResponses.getContent())
        .number(bookResponses.getNumber())
        .totalElements(bookResponses.getTotalElements())
        .size(bookResponses.getSize())
        .first(bookResponses.isFirst())
        .last(bookResponses.isLast())
        .totalPages(bookResponses.getTotalPages())
        .build();
  }

}
