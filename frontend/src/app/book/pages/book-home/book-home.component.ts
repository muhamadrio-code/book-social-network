import { Component } from '@angular/core';
import { BookService } from '../../book.service';
import { PageResponseBookResponse } from '../../types/page-response-book-response';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Observable, switchMap } from 'rxjs';

@Component({
  selector: 'app-book-home',
  templateUrl: './book-home.component.html',
})
export class BookHomeComponent {
  constructor(
    private bookService: BookService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  response$ = this.route.queryParamMap.pipe(
    switchMap((p) => this.findAllBooks(p))
  );

  private findAllBooks(p: ParamMap): Observable<PageResponseBookResponse> {
    const page = p.get('page') ?? '0';
    return this.bookService.findAllBooks({
      page: Number.parseInt(page),
    });
  }

  selectPage($event: number) {
    this.router.navigate(['./'], {
      relativeTo: this.route,
      queryParams: { page: $event },
    });
  }
}
