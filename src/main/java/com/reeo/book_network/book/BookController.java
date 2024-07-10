package com.reeo.book_network.book;

import com.reeo.book_network.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("books")
public class BookController {
  private final BookService service;

  @PostMapping
  ResponseEntity<?> saveBook(
      SaveBookRequest request,
      Authentication authentication
  ) {
    Integer savedBookId = service.save(request, (User) authentication.getPrincipal());
    return ResponseEntity.ok(savedBookId);
  }

  @PostMapping("/{book-id}")
  ResponseEntity<BookResponse> findBookById(
      @PathVariable("book-id") Integer bookId
  ) {
    return ResponseEntity.ok(service.findById(bookId));
  }

  @PostMapping
  ResponseEntity<PageResponse<BookResponse>> findAllBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBooks(page, size, (User) authentication.getPrincipal()));
  }

  @PostMapping("/owner")
  ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBooksByOwner(page, size, (User) authentication.getPrincipal()));
  }

  @PostMapping("/borrowed")
  ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBorrowedBook(page, size, (User) authentication.getPrincipal()));
  }

  @PostMapping("/returned")
  ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllReturnedBooks(page, size, (User) authentication.getPrincipal()));
  }

  @PatchMapping("/shareable/{book-id}")
  public ResponseEntity<Integer> updateShareableStatus(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.updateShareableStatus(bookId, (User) authentication.getPrincipal()));
  }

  @PatchMapping("/archived/{book-id}")
  public ResponseEntity<Integer> updateArchivedStatus(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.updateArchivedStatus(bookId, (User) authentication.getPrincipal()));
  }

  @PostMapping("borrow/{book-id}")
  public ResponseEntity<Integer> borrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.borrowBook(bookId, (User) authentication.getPrincipal()));
  }

  @PatchMapping("borrow/return/{book-id}")
  public ResponseEntity<Integer> returnBorrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.returnBorrowedBook(bookId, (User) authentication.getPrincipal()));
  }

  @PatchMapping("borrow/return/approve/{book-id}")
  public ResponseEntity<Integer> approveReturnBorrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.approveReturnedBook(bookId, (User) authentication.getPrincipal()));
  }

  @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
  public ResponseEntity<?> uploadBookCoverPicture(
      @PathVariable("book-id") Integer bookId,
      @Parameter()
      @RequestPart("file") MultipartFile file,
      Authentication authentication
  ) {
    service.uploadBookCoverPicture(bookId, (User) authentication.getPrincipal(), file);
    return ResponseEntity.accepted().build();
  }
}
