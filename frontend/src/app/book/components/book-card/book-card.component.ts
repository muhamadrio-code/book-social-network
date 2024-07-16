import { Component, Input } from '@angular/core';
import { BookResponse } from '../../types/book-response';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss'],
})
export class BookCardComponent {
  @Input() book: BookResponse | undefined;
}
