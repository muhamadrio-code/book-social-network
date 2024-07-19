package com.reeo.book_network.feedback;

import com.reeo.book_network.book.Book;
import com.reeo.book_network.book.BookRepository;
import com.reeo.book_network.common.PageResponse;
import com.reeo.book_network.exception.BookNotFoundException;
import com.reeo.book_network.exception.OperationNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final BookRepository bookRepository;
  private final FeedbackMapper feedbackMapper;
  private final FeedbackRepository feedbackRepository;

  public Integer save(FeedbackRequest request, Authentication authentication) throws OperationNotPermittedException {
    Book book = findBookByIdOrThrow(request.bookId());
    String userId = authentication.getName();
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("You cannot give a feedback for and archived or not shareable book");
    }
    if (Objects.equals(book.getCreatedBy(), userId)) {
      throw new OperationNotPermittedException("You cannot give feedback to your own book");
    }
    Feedback feedback = feedbackMapper.toFeedback(request);
    feedback.setCreatedBy(userId);
    feedback.setBook(book);

    Feedback savedFeedback = feedbackRepository.save(feedback);
    return savedFeedback.getId();
  }

  public PageResponse<FeedbackResponse> findAllFeedbackByBookId(Integer bookId, int size, int page, Authentication authentication) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
    String userId = authentication.getName();
    Function<Feedback, FeedbackResponse> mapper = (f) -> feedbackMapper.toFeedbackResponse(f, userId);
    Page<FeedbackResponse> pagedResponse = feedbackRepository
        .findAllByBookId(bookId, pageRequest)
        .map(mapper);

    return PageResponse.fromPaged(pagedResponse);
  }

  private Book findBookByIdOrThrow(Integer bookId) throws BookNotFoundException {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new BookNotFoundException("No book found with ID:: " + bookId));
  }

}
