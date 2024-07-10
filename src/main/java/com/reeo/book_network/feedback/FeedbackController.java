package com.reeo.book_network.feedback;

import com.reeo.book_network.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("feedback")
public class FeedbackController {

  private final FeedbackService service;

  @PostMapping
  ResponseEntity<?> createFeedback(
      @Valid @RequestBody FeedbackRequest request,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.save(request, (User) authentication.getPrincipal()));
  }

  @PostMapping("/book/{book-id}")
  ResponseEntity<?> findFeeddbackByBook(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable("book-id") Integer bookId,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.findAllFeedbackByBookId(
            bookId, size, page, (User) authentication.getPrincipal()));
  }

}
