/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../shared/strict-http-response';
import { RequestBuilder } from '../../shared/request-builder';

export interface FindFeeddbackByBook$Params {
  page?: number;
  size?: number;
  'book-id': number;
}

export function findFeeddbackByBook(
  http: HttpClient,
  rootUrl: string,
  params: FindFeeddbackByBook$Params,
  context?: HttpContext
): Observable<StrictHttpResponse<{}>> {
  const rb = new RequestBuilder(rootUrl, findFeeddbackByBook.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
    rb.path('book-id', params['book-id'], {});
  }

  return http
    .request(
      rb.build({ responseType: 'json', accept: 'application/json', context })
    )
    .pipe(
      filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<{}>;
      })
    );
}

findFeeddbackByBook.PATH = '/feedbacks/book/{book-id}';
