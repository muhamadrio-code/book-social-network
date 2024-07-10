package com.reeo.book_network.book;

import com.reeo.book_network.file.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

  public Book toBook(SaveBookRequest request) {
    return Book.builder()
        .title(request.getTitle())
        .authorName(request.getAuthorName())
        .bookCover(request.getBookCover())
        .synopsis(request.getSynopsis())
        .isbn(request.getIsbn())
        .shareable(request.isShareable())
        .archived(false)
        .build();
  }

  public BookResponse toResponse(Book book) {
    return BookResponse.builder()
        .id(book.getId())
        .archived(book.isArchived())
        .authorName(book.getAuthorName())
        .owner(book.getOwner().getFullname())
        .rate(book.getRate())
        .title(book.getTitle())
        .isbn(book.getIsbn())
        .synopsis(book.getSynopsis())
        .shareable(book.isShareable())
        .cover(FileUtils.readFileFromLocation(book.getBookCover()))
        .build();
  }

}
