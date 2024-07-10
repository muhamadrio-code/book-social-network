package com.reeo.book_network.feedback;

import com.reeo.book_network.book.Book;
import com.reeo.book_network.book.BookRepository;
import com.reeo.book_network.common.PageResponse;
import com.reeo.book_network.exception.BookNotFoundException;
import com.reeo.book_network.exception.OperationNotPermittedException;
import com.reeo.book_network.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final BookRepository bookRepository;
  private final FeedbackMapper feedbackMapper;
  private final FeedbackRepository feedbackRepository;

  public Integer save(FeedbackRequest request, User user) {
    Book book = findBookByIdOrThrow(request.bookId());
    if (book.isArchived() || !book.isShareable()) {
      throw new OperationNotPermittedException("You cannot give a feedback for and archived or not shareable book");
    }
    if (Objects.equals(book.getOwner().getId(), user.getId())) {
      throw new OperationNotPermittedException("You cannot give feedback to your own book");
    }
    Feedback feedback = feedbackMapper.toFeedback(request);
    feedback.setCreatedBy(user.getId());
    feedback.setLastModifiedBy(user.getId());
    feedback.setLastModifiedDate(LocalDateTime.now());
    feedback.setBook(book);

    Feedback savedFeedback = feedbackRepository.save(feedback);
    return savedFeedback.getId();
  }

  public PageResponse<FeedbackResponse> findAllFeedbackByBookId(Integer bookId, int size, int page, User user) {
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
    Function<Feedback, FeedbackResponse> mapper = (f) -> feedbackMapper.toFeedbackResponse(f, user.getId());
    Page<FeedbackResponse> pagedResponse = feedbackRepository
        .findAllByBookId(bookId, pageRequest)
        .map(mapper);

    return PageResponse.fromPaged(pagedResponse);
  }

  private Book findBookByIdOrThrow(Integer bookId) {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new BookNotFoundException("No book found with ID:: " + bookId));
  }

}
