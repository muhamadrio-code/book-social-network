package com.reeo.book_network.feedback;

import com.reeo.book_network.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Feedback")
@RequestMapping("feedbacks")
public class FeedbackController {

  private final FeedbackService service;

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"write"})
  )
  @PostMapping
  ResponseEntity<?> createFeedback(
      @Valid @RequestBody FeedbackRequest request,
      Authentication authentication
  ) {
    return ResponseEntity
        .ok(service.save(request, (User) authentication.getPrincipal()));
  }

  @Operation(
      security = @SecurityRequirement(name = "BearerAuth", scopes = {"read"})
  )
  @GetMapping("/book/{book-id}")
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
