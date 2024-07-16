import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookRoutingModule } from './book-routing.module';
import { MenuComponent } from './components/menu/menu.component';
import { BookCardComponent } from './components/book-card/book-card.component';
import { RatingComponent } from './components/rating/rating.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { RatingPipe } from './components/rating/rating.pipe';
import { PagerComponent } from './components/pager/pager.component';
import { RangePipe } from '../utils/range.pipe';
import { BookHomeComponent } from './pages/book-home/book-home.component';
import { MyBookComponent } from './pages/my-book/my-book.component';
import { BorrowedBookComponent } from './pages/borrowed-book/borrowed-book.component';
import { WaitingListComponent } from './pages/waiting-list/waiting-list.component';
import { ReturnedBookComponent } from './pages/returned-book/returned-book.component';
import { BookComponent } from './pages/book/book.component';

@NgModule({
  declarations: [
    BookHomeComponent,
    MenuComponent,
    BookCardComponent,
    RatingComponent,
    SearchBarComponent,
    RatingPipe,
    RangePipe,
    PagerComponent,
    BookHomeComponent,
    MyBookComponent,
    BorrowedBookComponent,
    WaitingListComponent,
    ReturnedBookComponent,
    BookComponent,
  ],
  imports: [CommonModule, BookRoutingModule],
})
export class BookModule {}
