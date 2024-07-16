/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../shared/strict-http-response';
import { RequestBuilder } from '../../shared/request-builder';

export interface Activate$Params {
  token: string;
}

export function activate(
  http: HttpClient,
  rootUrl: string,
  params: Activate$Params,
  context?: HttpContext
): Observable<StrictHttpResponse<{}>> {
  const rb = new RequestBuilder(rootUrl, activate.PATH, 'get');
  if (params) {
    rb.query('token', params.token, {});
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

activate.PATH = '/auth/activate-account';
