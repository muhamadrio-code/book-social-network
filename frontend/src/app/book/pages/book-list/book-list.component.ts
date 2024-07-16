import { Component, OnInit } from '@angular/core';
import { Perform } from 'src/app/utils/perform';
import { BookService } from '../../book.service';
import { PageResponseBookResponse } from '../../types/page-response-book-response';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss'],
})
export class BookListComponent implements OnInit {
  constructor(private bookService: BookService) {}

  response$: Perform<PageResponseBookResponse> = new Perform();

  ngOnInit(): void {
    this.response$.load(this.bookService.findAllBooks());
  }

  selectPage($event: number) {}
}
