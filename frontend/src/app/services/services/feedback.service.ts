/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createFeedback } from '../fn/feedback/create-feedback';
import { CreateFeedback$Params } from '../fn/feedback/create-feedback';
import { findFeeddbackByBook } from '../fn/feedback/find-feeddback-by-book';
import { FindFeeddbackByBook$Params } from '../fn/feedback/find-feeddback-by-book';

@Injectable({ providedIn: 'root' })
export class FeedbackService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createFeedback()` */
  static readonly CreateFeedbackPath = '/feedbacks';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createFeedback()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedback$Response(params: CreateFeedback$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return createFeedback(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createFeedback$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedback(params: CreateFeedback$Params, context?: HttpContext): Observable<{
}> {
    return this.createFeedback$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `findFeeddbackByBook()` */
  static readonly FindFeeddbackByBookPath = '/feedbacks/book/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findFeeddbackByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  findFeeddbackByBook$Response(params: FindFeeddbackByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return findFeeddbackByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findFeeddbackByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findFeeddbackByBook(params: FindFeeddbackByBook$Params, context?: HttpContext): Observable<{
}> {
    return this.findFeeddbackByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

}
