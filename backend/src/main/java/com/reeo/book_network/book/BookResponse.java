package com.reeo.book_network.book;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookResponse {
  private Integer id;
  private String title;
  private String authorName;
  private String isbn;
  private String synopsis;
  private String owner;
  private byte[] cover;
  private double rate;
  private boolean archived;
  private boolean shareable;
}
