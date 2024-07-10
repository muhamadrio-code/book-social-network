package com.reeo.book_network.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class SaveBookRequest {
  @NotNull
  @NotEmpty
  private String title;
  @NotNull
  @NotEmpty
  private String authorName;
  @NotNull
  @NotEmpty
  private String isbn;
  @NotNull
  @NotEmpty
  private String synopsis;
  @NotNull
  @NotEmpty
  private String bookCover;
  @NotNull
  private boolean shareable;
}
