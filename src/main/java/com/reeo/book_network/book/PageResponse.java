package com.reeo.book_network.book;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
  private List<T> content;
  private int size;
  private int number;
  private int totalPages;
  private long totalElements;
  private boolean first;
  private boolean last;
}
