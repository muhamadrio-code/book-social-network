package com.reeo.book_network.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.domain.Page;

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

  @JsonIgnore
  public static <P> PageResponse<P> fromPaged(Page<P> object) {
    return PageResponse.<P>builder()
        .content(object.getContent())
        .number(object.getNumber())
        .totalElements(object.getTotalElements())
        .size(object.getSize())
        .first(object.isFirst())
        .last(object.isLast())
        .totalPages(object.getTotalPages())
        .build();
  }
}
