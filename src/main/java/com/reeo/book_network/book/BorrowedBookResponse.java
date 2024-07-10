package com.reeo.book_network.book;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BorrowedBookResponse {
  private Integer id;
  private String title;
  private String authorName;
  private String isbn;
  private String coverPicture;
  private double rate;
  private boolean returned;
  private boolean returnApproved;
}
