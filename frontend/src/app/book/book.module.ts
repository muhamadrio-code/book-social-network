import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookListComponent } from './pages/book-list/book-list.component';
import { BookRoutingModule } from './book-routing.module';
import { MenuComponent } from './components/menu/menu.component';
import { BookCardComponent } from './components/book-card/book-card.component';
import { RatingComponent } from './components/rating/rating.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { RatingPipe } from './components/utils/rating.pipe';
import { PagerComponent } from './components/pager/pager.component';
import { RangePipe } from './components/utils/range.pipe';

@NgModule({
  declarations: [
    BookListComponent,
    MenuComponent,
    BookCardComponent,
    RatingComponent,
    SearchBarComponent,
    RatingPipe,
    RangePipe,
    PagerComponent,
  ],
  imports: [CommonModule, BookRoutingModule],
})
export class BookModule {}
