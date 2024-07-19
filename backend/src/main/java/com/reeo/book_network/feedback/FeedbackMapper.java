package com.reeo.book_network.feedback;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class FeedbackMapper {

  public Feedback toFeedback(FeedbackRequest feedbackRequest) {
    return Feedback.builder()
        .comment(feedbackRequest.comment())
        .note(feedbackRequest.note())
        .lastModifiedDate(LocalDateTime.now())
        .build();
  }

  @SneakyThrows
  public FeedbackResponse toFeedbackResponse(Feedback feedback, String userId) {
    return FeedbackResponse.builder()
        .comment(feedback.getComment())
        .note(feedback.getNote())
        .ownFeedback(Objects.equals(feedback.getBook().getCreatedBy(), userId))
        .build();
  }

}
