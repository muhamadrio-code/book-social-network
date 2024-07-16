import { Route, RouterModule } from '@angular/router';
import { BookListComponent } from './pages/book-list/book-list.component';
import { NgModule } from '@angular/core';

const routes: Route[] = [
  {
    path: 'books',
    component: BookListComponent,
  },
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forChild(routes)],
})
export class BookRoutingModule {}
