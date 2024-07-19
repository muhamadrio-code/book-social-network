package com.reeo.book_network.book;

import com.reeo.book_network.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("books")
@Tag(name = "Book")
public class BookController {
  private final BookService service;

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PostMapping
  ResponseEntity<?> saveBook(
      @RequestBody @Valid SaveBookRequest request,
      Authentication authentication
  ) {
    Integer savedBookId = service.save(request, authentication);
    return ResponseEntity.ok(savedBookId);
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping("/{book-id}")
  ResponseEntity<BookResponse> findBookById(
      @PathVariable("book-id") Integer bookId
  ) {
    return ResponseEntity.ok(service.findById(bookId));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping
  ResponseEntity<PageResponse<BookResponse>> findAllBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBooks(page, size, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping("/owner")
  ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBooksByOwner(page, size, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping("/borrowed")
  ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllBorrowedBook(page, size, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping("/returned")
  ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllReturnedBooks(page, size, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PatchMapping("/shareable/{book-id}")
  public ResponseEntity<Integer> updateShareableStatus(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.updateShareableStatus(bookId, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PatchMapping("/archived/{book-id}")
  public ResponseEntity<Integer> updateArchivedStatus(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.updateArchivedStatus(bookId, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PostMapping("borrow/{book-id}")
  public ResponseEntity<Integer> borrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.borrowBook(bookId, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PatchMapping("borrow/return/{book-id}")
  public ResponseEntity<Integer> returnBorrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.returnBorrowedBook(bookId, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PatchMapping("borrow/return/approve/{book-id}")
  public ResponseEntity<Integer> approveReturnBorrowBook(
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity.ok(service.approveReturnedBook(bookId, authentication));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
  public ResponseEntity<?> uploadBookCoverPicture(
      @PathVariable("book-id") Integer bookId,
      @Parameter()
      @RequestPart("file") MultipartFile file,
      Authentication authentication
  ) {
    service.uploadBookCoverPicture(bookId, authentication, file);
    return ResponseEntity.accepted().build();
  }
}
