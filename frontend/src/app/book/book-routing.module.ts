import { Route, RouterModule } from '@angular/router';
import { BookHomeComponent } from './pages/book-home/book-home.component';
import { NgModule } from '@angular/core';
import { BorrowedBookComponent } from './pages/borrowed-book/borrowed-book.component';
import { MyBookComponent } from './pages/my-book/my-book.component';
import { WaitingListComponent } from './pages/waiting-list/waiting-list.component';
import { ReturnedBookComponent } from './pages/returned-book/returned-book.component';
import { BookComponent } from './pages/book/book.component';
import { authGuard } from '../auth/auth.guard';

const routes: Route[] = [
  {
    path: '',
    component: BookComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        canActivateChild: [authGuard],
        children: [
          {
            path: '',
            component: BookHomeComponent,
          },
          {
            path: 'borrowed-book',
            component: BorrowedBookComponent,
          },
          {
            path: 'my-book',
            component: MyBookComponent,
          },
          {
            path: 'returned-book',
            component: ReturnedBookComponent,
          },
          {
            path: 'waiting-list',
            component: WaitingListComponent,
          },
        ],
      },
    ],
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routes)],
})
export class BookRoutingModule {}
