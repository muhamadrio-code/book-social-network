package com.reeo.book_network.feedback;

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

  public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer ownerId) {
    return FeedbackResponse.builder()
        .comment(feedback.getComment())
        .note(feedback.getNote())
        .ownFeedback(Objects.equals(feedback.getBook().getOwner().getId(), ownerId))
        .build();
  }

}
