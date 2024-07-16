import { Component, OnInit } from '@angular/core';
import { Perform } from 'src/app/modules/utils/perform';
import {
  BookResponse,
  PageResponseBookResponse,
} from 'src/app/services/models';
import { BookService } from '../../book.service';

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
