package com.reeo.book_network.feedback;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedbackResponse {
  private Double note;
  private String comment;
  private boolean ownFeedback;
}
